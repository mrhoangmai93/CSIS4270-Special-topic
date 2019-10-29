package com.hoangtuthinhthao.languru.views.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.api.ApiClient;
import com.hoangtuthinhthao.languru.controllers.api.ApiService;
import com.hoangtuthinhthao.languru.controllers.authentication.AuthHelpers;
import com.hoangtuthinhthao.languru.controllers.authentication.SessionControl;
import com.hoangtuthinhthao.languru.models.User;
import com.hoangtuthinhthao.languru.views.popUp.PopUp;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ApiService apiAuthInterface;
    private EditText  email, password;
    private TextView createNewAccount;
    private Button login;
    private User user;
    private SharedPreferences prefs;
    private SessionControl session;
    Dialog dialog;
    ProgressDialog progressDialog;


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
        apiAuthInterface = ApiClient.getClient().create(ApiService.class);

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


}