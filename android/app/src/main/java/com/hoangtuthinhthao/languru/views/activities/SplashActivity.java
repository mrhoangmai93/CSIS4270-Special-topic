package com.hoangtuthinhthao.languru.views.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.authentication.AuthChecker;
import com.hoangtuthinhthao.languru.controllers.authentication.SessionControl;

public class SplashActivity extends AppCompatActivity {

    SessionControl sessionControl;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionControl = new SessionControl(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        AuthChecker authChecker = new AuthChecker(sessionControl);

        if(authChecker.isAuthenticated()) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        progressDialog.dismiss();
        finish();
    }
}
