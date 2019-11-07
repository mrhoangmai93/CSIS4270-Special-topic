package com.hoangtuthinhthao.languru.views.activities;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.helpers.FragmentControl;
import com.hoangtuthinhthao.languru.controllers.helpers.GlideApp;
import com.hoangtuthinhthao.languru.controllers.loadServices.game.LoadGame;
import com.hoangtuthinhthao.languru.controllers.loadServices.topic.LoadTopicCallback;
import com.hoangtuthinhthao.languru.models.game.LoadGameCallback;
import com.hoangtuthinhthao.languru.models.responses.Lesson;
import com.hoangtuthinhthao.languru.views.fragments.games.GameCenterFragment;
import com.hoangtuthinhthao.languru.views.fragments.games.GamePracticeFragment;
import com.hoangtuthinhthao.languru.views.fragments.games.OnGameCenterInteraction;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements OnGameCenterInteraction {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        getSupportActionBar().hide();

        // initialize fragment
        fm = getSupportFragmentManager();
        FragmentControl.changeFragment(fm, ft, R.id.game_activity_container, GameCenterFragment.newInstance());

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
    }


    @Override
    public void onGameItemClick(int id) {
        switch (id) {
            case  R.id.btnPractice:
                Log.i("btn", "practice");
                gameLoader.byNumberWord(currentNumberOfWord, callback);

                break;
            case R.id.btnChallenge:

                break;
        }
    }

    @Override
    public void onGameComplete(int numberOfWord) {
        count = 0;
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
                    Toast.makeText(this, "You have completed all level", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackButtonPressed() {
        Fragment fragment =  fm.findFragmentById(R.id.game_activity_container);
        if(fragment instanceof GamePracticeFragment) {
           // FragmentControl.changeFragment(fm, ft, R.id.game_activity_container, GameCenterFragment.newInstance());
            GameActivity.this.onBackPressed();
        } else {
            finish();
        }
    }

    private void downloadImage(final Lesson lesson) {

        GlideApp.with(getApplicationContext())
                .asBitmap()
                .load(lesson.getImage())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady( Bitmap resource,  Transition<? super Bitmap> transition) {
                        lesson.setImageBitmap(resource);

                        count++;

                        if( count == lessonList.size()) {

                            FragmentControl.changeFragment(fm, ft, R.id.game_activity_container, GamePracticeFragment.newInstance(currentNumberOfWord,lessonList));
                        }
                    }

                    @Override
                    public void onLoadCleared( Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed( Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        Log.i("count", "Failed");
                    }

                });
    }
}
