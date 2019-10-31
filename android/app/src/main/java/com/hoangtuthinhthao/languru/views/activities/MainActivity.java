package com.hoangtuthinhthao.languru.views.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.authentication.AuthChecker;
import com.hoangtuthinhthao.languru.controllers.authentication.SessionControl;
import com.hoangtuthinhthao.languru.controllers.loadServices.LoadLesson;
import com.hoangtuthinhthao.languru.controllers.loadServices.LoadLessonCallback;
import com.hoangtuthinhthao.languru.models.responses.Lesson;
import com.hoangtuthinhthao.languru.views.fragments.LessonFragment;
import com.hoangtuthinhthao.languru.views.fragments.OnFragmentInteractionListener;
import com.hoangtuthinhthao.languru.views.fragments.TopicFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {
    public static final String LOAD_TOPIC_DONE = "LOAD_TOPIC_DONE";
    public static final String LOAD_TOPIC_FAILED = "LOAD_TOPIC_FAILED";

    // drawer Layout
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle t;

    // hard code topics
    private ArrayList<String> topicList = new ArrayList<>(Arrays.asList("greeting", "music", "animals", "furniture", "school"));

    //Fragment
    private FragmentManager fm;
    private FragmentTransaction ft;
    private TopicFragment topicFragment;

    // load lesson
    private LoadLesson loadLesson;

    //broadcast Receiver
    BroadcastReceiver response;
    // Load Lesson callback
    private LoadLessonCallback callback;

    //Session
    private SessionControl sessionControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * BEGIN NAVIGATION DRAWER
         */
        drawerLayout =  findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, drawerLayout,R.string.Open, R.string.Close);

        drawerLayout.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*
         * END NAVIGATION DRAWER
         */

        //Fragments

        fm = getSupportFragmentManager();
        changeFragment(TopicFragment.newInstance(topicList));

        // initialize load lesson
        loadLesson = new LoadLesson(this);

        //initialize broad receiver
        response = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handleResponse(intent);
            }
        };

        //Session
        sessionControl = new SessionControl(this);

        // initialze callback for load lesson
        callback = new LoadLessonCallback() {
            @Override
            public void successLoadLesson(String topicName, ArrayList<Lesson> lessons) {
                changeFragment(LessonFragment.newInstance(topicName, lessons));
            }

            @Override
            public void failLoadLesson(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        AuthChecker authChecker = new AuthChecker(sessionControl);

        if(!authChecker.isAuthenticated()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout :
                sessionControl.setJwtToken(null);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        // Unregister since the activity is paused.
        super.onPause();
        unregisterReceiver(response);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // An IntentFilter can match against actions, categories, and data
        IntentFilter filter = new IntentFilter();
        filter.addAction(LOAD_TOPIC_DONE);
        filter.addAction(LOAD_TOPIC_FAILED);

        // register broadcast
        registerReceiver(response,filter);
    }
    /**
     * Change fragment function
     * @param fg Fragment to change
     */
    private void changeFragment(Fragment fg) {
        ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        ft.replace(R.id.main_activity_container, fg).
                addToBackStack(null).commit();
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
        Log.i("positoin", String.valueOf(position));
        loadLesson.byTopic(topicList.get(position), callback);
    }

    @Override
    public void onBackButtonPressed() {
        Fragment fragment =  fm.findFragmentById(R.id.main_activity_container);
        if(fragment instanceof LessonFragment) {

            changeFragment(TopicFragment.newInstance(topicList));
        } else {
            finish();
        }
    }

    /**
     * This function handler the responses received from broadcast
     * @param intent intent received
     */
    private void handleResponse(Intent intent) {
        switch (intent.getAction()){
            case LOAD_TOPIC_DONE :
                Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT).show();
                break;
            case LOAD_TOPIC_FAILED :
                Toast.makeText(this, "Failed load", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
