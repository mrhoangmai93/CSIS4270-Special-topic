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
import com.hoangtuthinhthao.languru.views.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthHelpers {
    /**
     * Sign up user
     * @param apiInterface
     * @param user
     */
    public static void signupUser(final Context context, ApiService apiInterface, final SessionControl session, final User user) {
        // Set up progressbar before call
        Call<DefaultResponse> call = apiInterface.registerUser(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), "android");

        final Gson gson = new Gson();
        final String json = gson.toJson(user);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.isSuccessful()){
                    response.body(); // have your all data
                    List<Session> sessions = response.body().getSessions();
                    String token = "";
                    if(sessions != null)
                        token = sessions.get(0).getAccessToken();
                    //Log.i("token", token);
                    session.setJwtToken(token);

                    context.startActivity(new Intent(context, MainActivity.class));
                }
                else   Toast.makeText(context, (CharSequence) response.errorBody(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.e("SignupFragment", t.getMessage());

            }

        });
    }

    /**
     * login user
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
                    //Log.i("token", token);
                    session.setJwtToken(token);

                    context.startActivity(new Intent(context, MainActivity.class));
                }
                else   Toast.makeText(context, (CharSequence) response.errorBody(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("Login Failed", t.getMessage());

            }

        });
    }
}
