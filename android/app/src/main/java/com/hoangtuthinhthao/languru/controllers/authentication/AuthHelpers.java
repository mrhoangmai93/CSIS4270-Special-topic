package com.hoangtuthinhthao.languru.controllers.authentication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hoangtuthinhthao.languru.controllers.api.ApiService;
import com.hoangtuthinhthao.languru.models.DefaultResponse;
import com.hoangtuthinhthao.languru.models.Session;
import com.hoangtuthinhthao.languru.models.responses.ResponseLogin;
import com.hoangtuthinhthao.languru.models.User;
import com.hoangtuthinhthao.languru.models.responses.ResponseRegister;
import com.hoangtuthinhthao.languru.views.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hoangtuthinhthao.languru.views.activities.LoginActivity.*;


public class AuthHelpers {
    /**
     * Sign up user
     * @param apiInterface
     * @param user
     */
    public static void signupUser(final Context context, ApiService apiInterface, final User user) {
        // Set up progressbar before call
        Call<ResponseRegister> call = apiInterface.registerUser(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), "android");

        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if(response.isSuccessful()){
                    response.body(); // have your all data
                    List<Session> sessions = response.body().getSessions();
                    String token = "";
                    if(sessions != null)
                        token = sessions.get(0).getAccessToken();

                    Intent intent = new Intent();
                    intent.setAction(REGISTER_DONE);
                    intent.putExtra("token",token);
                    context.sendBroadcast(intent);


                }
                else {
                    Intent intent = new Intent();
                    intent.setAction(AUTH_FAILED);
                    intent.putExtra("message",response.errorBody().toString());
                    context.sendBroadcast(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Log.e("SignupFragment", t.getMessage());
                Intent intent = new Intent();
                intent.setAction(AUTH_FAILED);
                intent.putExtra("message", t.getMessage());
                context.sendBroadcast(intent);
            }

        });
    }

    /**
     * login user broadcast the token after finished
     * @param context
     * @param apiInterface
     * @param session
     * @param email
     * @param password
     */
    public static void loginUser(final Context context, ApiService apiInterface, final SessionControl session, final String email, String password) {
        // Set up progressbar before call
        Call<ResponseLogin> call = apiInterface.loginUser(email, password, "android");

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if(response.isSuccessful()){
                    response.body(); // have your all data
                    List<Session> sessions = response.body().getSessions();
                    String token = "";
                    if(sessions != null)
                         token = sessions.get(0).getAccessToken();

                    Intent intent = new Intent();
                    intent.setAction(LOGIN_DONE);
                    intent.putExtra("token",token);
                    context.sendBroadcast(intent);
                }
                else   Toast.makeText(context, "Failed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("Login Failed", t.getMessage());
                Intent intent = new Intent();
                intent.setAction(AUTH_FAILED);
                intent.putExtra("message", t.getMessage());
                context.sendBroadcast(intent);
            }

        });
    }
}
