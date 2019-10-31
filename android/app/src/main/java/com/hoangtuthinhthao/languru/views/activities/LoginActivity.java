package com.hoangtuthinhthao.languru.views.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.api.ApiClient;
import com.hoangtuthinhthao.languru.controllers.api.ApiAuthService;
import com.hoangtuthinhthao.languru.controllers.authentication.AuthHelpers;
import com.hoangtuthinhthao.languru.controllers.authentication.SessionControl;
import com.hoangtuthinhthao.languru.models.User;
import com.hoangtuthinhthao.languru.views.popUp.PopUp;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String REGISTER_DONE = "REGISTER_DONE";
    public static final String AUTH_FAILED = "AUTH_FAILED";
    public static final String LOGIN_DONE = "LOGIN_DONE";


    private ApiAuthService apiAuthInterface;
    private EditText  email, password;
    private TextView createNewAccount;
    private Button login;
    private User user;
    private SharedPreferences prefs;
    private SessionControl session;
    Dialog dialog;
    ProgressDialog progressDialog;
    private BroadcastReceiver response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initialize session
        session = new SessionControl(LoginActivity.this);

        //initialize dialog and progress dialog
        dialog = new Dialog(this);
        progressDialog = new ProgressDialog(this);

        // find Views
        createNewAccount = findViewById(R.id.createAccount);
        login = findViewById(R.id.login);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);

        // set on click
        createNewAccount.setOnClickListener(this);
        login.setOnClickListener(this);
        //
        apiAuthInterface = ApiClient.getClient().create(ApiAuthService.class);

        //initialize the broadcast
        response = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()){
                    case REGISTER_DONE :
                    case LOGIN_DONE:
                        Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show();
                        registerDone(intent);
                        break;
                    case AUTH_FAILED :
                        String msg = intent.getStringExtra("message");
                        Log.i("auth error", msg);
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        session.setJwtToken("");
                        break;
                }
            }
        };
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
        filter.addAction(REGISTER_DONE);
        filter.addAction(LOGIN_DONE);
        filter.addAction(AUTH_FAILED);

        // register broadcast
        registerReceiver(response,filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.createAccount:
                PopUp.openRegisterPopup( dialog, LoginActivity.this, progressDialog, apiAuthInterface);
                break;
            case  R.id.login:
                    loginUser();
                break;
                default:
                    break;
        }
    }

    /**
     * This function to login user
     */
    private void loginUser() {
        String txtEmail = email.getText().toString().trim();
        String txtPassword = password.getText().toString().trim();
        if(txtEmail.isEmpty() || txtPassword.isEmpty()) {
            Toast.makeText(this, "Email/Password cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthHelpers.loginUser(this, apiAuthInterface,session, txtEmail, txtPassword);
    }

    /**
     * This function set the token and change to main activity
     * @param intent received from response broadcast
     */
    private void registerDone(Intent intent) {
        String token = intent.getStringExtra("token");
        session.setJwtToken(token);

        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }
}