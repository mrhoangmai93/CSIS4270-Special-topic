package com.hoangtuthinhthao.languru.views.activities;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.helpers.FragmentControl;
import com.hoangtuthinhthao.languru.controllers.loadServices.game.LoadGame;
import com.hoangtuthinhthao.languru.controllers.loadServices.lesson.LoadLessonCallback;
import com.hoangtuthinhthao.languru.models.responses.Lesson;
import com.hoangtuthinhthao.languru.views.fragments.games.GameCenterFragment;
import com.hoangtuthinhthao.languru.views.fragments.OnFragmentInteractionListener;
import com.hoangtuthinhthao.languru.views.fragments.games.GamePracticeFragment;
import com.hoangtuthinhthao.languru.views.fragments.games.OnGameCenterInteraction;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements OnGameCenterInteraction {
    //Fragment
    private FragmentManager fm;
    private FragmentTransaction ft;

    // game Loader
    LoadGame gameLoader;
    LoadLessonCallback callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        // initialize fragment
        fm = getSupportFragmentManager();
        FragmentControl.changeFragment(fm, ft, R.id.game_activity_container, GameCenterFragment.newInstance("",""));

        // intialize load
        gameLoader = new LoadGame(this);

        // load lesson callback
        callback = new LoadLessonCallback() {
            @Override
            public void successLoadLesson(String numberWords, ArrayList<Lesson> lessons) {
                FragmentControl.changeFragment(fm, ft, R.id.game_activity_container, GamePracticeFragment.newInstance(Integer.parseInt(numberWords),lessons));
            }

            @Override
            public void failLoadLesson(String message) {
                Log.i("failed", message);
            }
        };
    }


    @Override
    public void onGameItemClick(int id) {
        switch (id) {
            case  R.id.btnPractice:

                gameLoader.byNumberWord(6, callback);

                break;
            case R.id.btnChallenge:

                break;
        }
    }
}
