package com.hoangtuthinhthao.languru.views.activities;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.authentication.AuthHelpers;
import com.hoangtuthinhthao.languru.controllers.authentication.SessionControl;

import java.io.IOException;
import java.io.InputStream;

import static com.hoangtuthinhthao.languru.views.activities.LoginActivity.apiAuthInterface;
import static com.hoangtuthinhthao.languru.views.activities.MainActivity.sessionControl;

public class TranslationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String API_KEY = "AIzaSyBAgxCdYfGnP1XDN4Zd1olrx34OGoVUY6o";

    private EditText inputToTranslate;
    private TextView translatedTv;

    private String originalText;
    private String translatedText;
    Translate translate;
    // drawer Layout
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        /*
         * BEGIN NAVIGATION DRAWER
         */
        drawerLayout = findViewById(R.id.drawer_layout);
        t = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);

        drawerLayout.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*
         * END NAVIGATION DRAWER
         */
        inputToTranslate = findViewById(R.id.translateInput);
        translatedTv = findViewById(R.id.translateResult);
        Button translateButton = findViewById(R.id.btnTranslate);

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //If there is internet connection, get translate service and start translation:
                    getTranslateService();
                    translate();


            }
        });

    }


    public void getTranslateService() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try (InputStream is = getResources().openRawResource(R.raw.credentials)) {

            //Get credentials:
            final GoogleCredentials myCredentials = GoogleCredentials.fromStream(is);

            //Set credentials and get translate service:
            TranslateOptions translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build();
            translate = translateOptions.getService();

        } catch (IOException ioe) {
            ioe.printStackTrace();

        }
    }

    public void translate() {

        //Get input text to be translated:
        originalText = inputToTranslate.getText().toString();
        Translation translation = translate.translate(originalText, Translate.TranslateOption.targetLanguage("en"), Translate.TranslateOption.model("base"));
        translatedText = translation.getTranslatedText();

        //Translated text and original text are set to TextViews:
        translatedTv.setText(translatedText);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                sessionControl.setJwtToken(null);
                AuthHelpers.logoutUser(this, apiAuthInterface, sessionControl.getRefreshToken());
                startActivity(new Intent(TranslationActivity.this, LoginActivity.class));
                break;
            case R.id.gameCenter:
                startActivity(new Intent(TranslationActivity.this, GameActivity.class));
                break;
            case R.id.AI:
                startActivity(new Intent(TranslationActivity.this, AIActivity.class));
                break;
            case R.id.topics:
                startActivity(new Intent(TranslationActivity.this, MainActivity.class));
                break;
        }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
