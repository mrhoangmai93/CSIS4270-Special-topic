package com.hoangtuthinhthao.languru.views.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.authentication.SessionControl;
import com.hoangtuthinhthao.languru.controllers.helpers.FragmentControl;
import com.hoangtuthinhthao.languru.controllers.helpers.GlideApp;
import com.hoangtuthinhthao.languru.controllers.loadServices.game.LoadGame;
import com.hoangtuthinhthao.languru.controllers.timer.TimerControl;
import com.hoangtuthinhthao.languru.models.game.LoadGameCallback;
import com.hoangtuthinhthao.languru.models.io.IoResponse;
import com.hoangtuthinhthao.languru.models.io.Payload;
import com.hoangtuthinhthao.languru.models.responses.Lesson;
import com.hoangtuthinhthao.languru.views.fragments.games.ChalengeGameFragment;
import com.hoangtuthinhthao.languru.views.fragments.games.GameCenterFragment;
import com.hoangtuthinhthao.languru.views.fragments.games.GameInteraction;
import com.hoangtuthinhthao.languru.views.fragments.games.GamePracticeFragment;
import com.hoangtuthinhthao.languru.views.fragments.games.OnGameCenterInteraction;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.hoangtuthinhthao.languru.views.popUp.PopUp;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements OnGameCenterInteraction, GameInteraction {
    public static final String socketUrl = "http://10.0.2.2:5001/room";
    //Fragment
    private FragmentManager fm;
    private FragmentTransaction ft;

    // game Loader
    LoadGame gameLoader;
    LoadGameCallback callback;

    // count for load image
    private int count = 0;
    private ArrayList<Lesson> lessonList;
    private int currentNumberOfWord = 6;
    private Dialog dialog;
    ProgressDialog progressDialog;
    // socket Io
    public static Socket mSocket;

    {
        try {
            mSocket = IO.socket(socketUrl);
        } catch (URISyntaxException e) {
        }
    }

    private Emitter.Listener ioListener;
    private SessionControl session;
    //game Type
    private String gameType = "practice";
    // Gson object
    Gson gson = new Gson();
    public static Payload gamePayload;

    // Timer control
    public static TimerControl timerControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getSupportActionBar().hide();

        // initialize fragment
        fm = getSupportFragmentManager();
        FragmentControl.changeFragment(fm, ft, R.id.game_activity_container, GameCenterFragment.newInstance());

        //initilize session
        session = new SessionControl(this);

        dialog = new Dialog(this);
        progressDialog = new ProgressDialog(this);
        //initialize socket
        mSocket.connect();

        // intialize load
        gameLoader = new LoadGame(this);

        // load lesson callback
        callback = new LoadGameCallback() {
            @Override
            public void successLoadGame(int numberOfWord, ArrayList<Lesson> lessons) {
                lessonList = lessons;
                for (Lesson ls : lessonList) {
                    downloadImage(ls);
                }
            }

            @Override
            public void failLoadGame(String message) {

            }

        };

        //timer
        timerControl = new TimerControl(this);

        //initialize io
        ioListener = new Emitter.Listener() {

            @Override
            public void call(final Object... args) {
                GameActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        Log.i("data", data.toString());
                        String type;
                        String payload;
                        try {
                            type = data.getString("type");
                            payload = data.getString("payload");
                            switch (type) {
                                case "SERVER_CREATE_GAME_SUCCESS":
                                    PopUp.openWaitingPlayerPopup(dialog, payload);
                                    break;
                                case "SERVER_ENOUGH_PLAYERS":
                                    IoResponse response = gson.fromJson(data.toString(), IoResponse.class);
                                    //set game payload
                                    gamePayload = response.getPayload();
                                    //start game loader
                                    gameLoader.byNumberWord(currentNumberOfWord, callback);
                                    //close dialog
                                    dialog.dismiss();
                                    break;
                                case "SERVER_GAME_ERROR":
                                    Toast.makeText(GameActivity.this, payload, Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } catch (JSONException e) {
                            return;
                        }

                    }
                });
            }
        };

        //add socket listen on event
        mSocket.on("action", ioListener);
    }


    @Override
    public void onGameItemClick(int id) {
        switch (id) {
            case R.id.btnPractice:
                gameType = "practice";
                gameLoader.byNumberWord(currentNumberOfWord, callback);

                break;
            case R.id.btnChallenge:
                gameType = "challenge";
                FragmentControl.changeFragment(fm, ft, R.id.game_activity_container, ChalengeGameFragment.newInstance());

                break;
        }
    }

    @Override
    public void onGameComplete(int numberOfWord) {
        count = 0;
        if (gameType.equals("challenge")) {
            timerControl.stopChallengeTimer();
        }
        switch (numberOfWord) {
            case 6:
                currentNumberOfWord = 10;
                gameLoader.byNumberWord(10, callback);
                break;
            case 10:
                currentNumberOfWord = 15;
                gameLoader.byNumberWord(15, callback);
                break;
            default:
                mSocket.emit("player.finishGame");
                Toast.makeText(this, "You have completed all level", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackButtonPressed() {
        Fragment fragment = fm.findFragmentById(R.id.game_activity_container);
        if (fragment instanceof GamePracticeFragment) {
            // FragmentControl.changeFragment(fm, ft, R.id.game_activity_container, GameCenterFragment.newInstance());
            GameActivity.this.onBackPressed();
        } else {
            finish();
        }
    }

    @Override
    public void onPlayerReady() {

    }

    @Override
    public void onChalengeGameInteraction(String type, String code) {

        String fullName = session.getFullName();

        JSONObject payload = new JSONObject();
        try {
            payload.put("code", code);
            payload.put("name", fullName);
            if (type.equals("join")) {
                mSocket.emit("join", payload);
            } else if (type.equals("create")) {

                mSocket.emit("create", payload);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void downloadImage(final Lesson lesson) {

        GlideApp.with(getApplicationContext())
                .asBitmap()
                .load(lesson.getImage())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        lesson.setImageBitmap(resource);

                        count++;

                        if (count == lessonList.size()) {
                            if (gameType.equals("challenge")) {
                                Log.i("dismiss", "dismiss");
                                // progressDialog.dismiss();

                            }

                            FragmentControl.changeFragment(fm, ft, R.id.game_activity_container, GamePracticeFragment.newInstance(gameType, currentNumberOfWord, lessonList));
                        }
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        Log.i("count", "Failed");
                    }

                });
    }

}
