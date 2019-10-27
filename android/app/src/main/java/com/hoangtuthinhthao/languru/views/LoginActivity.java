package com.hoangtuthinhthao.languru.views;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.api.ApiService;
import com.hoangtuthinhthao.languru.controllers.authentication.Session;
import com.hoangtuthinhthao.languru.controllers.authentication.TokenRenewInterceptor;
import com.hoangtuthinhthao.languru.controllers.authentication.UserSession;
import com.hoangtuthinhthao.languru.models.User;
import com.hoangtuthinhthao.languru.views.popUp.PopUp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ApiService apiService;
    private EditText firstName, lastName, email, password, comfirmPassword;
    private TextView createNewAccount;
    private Button register;
    private User user;
    private SharedPreferences prefs;
    private UserSession session;

    Dialog dialog;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize dialog and progress dialog
        dialog = new Dialog(this);
        progressDialog = new ProgressDialog(this);

        // find Views
        createNewAccount = findViewById(R.id.createAccount);
        createNewAccount.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.createAccount:
                PopUp.openRegisterPopup( dialog, LoginActivity.this, progressDialog);
                break;
                default:
                    break;
        }
    }
}
