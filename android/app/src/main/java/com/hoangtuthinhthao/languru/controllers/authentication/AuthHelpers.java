package com.hoangtuthinhthao.languru.controllers.authentication;

import android.util.Log;

import com.google.gson.Gson;
import com.hoangtuthinhthao.languru.controllers.api.ApiService;
import com.hoangtuthinhthao.languru.models.DefaultResponse;
import com.hoangtuthinhthao.languru.models.responses.ResponseLogin;
import com.hoangtuthinhthao.languru.models.Session;
import com.hoangtuthinhthao.languru.models.User;

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
    public static void signupUser(ApiService apiInterface, final User user) {
        // Set up progressbar before call
        Call<DefaultResponse> call = apiInterface.registerUser(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), "android");

        final Gson gson = new Gson();
        final String json = gson.toJson(user);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Log.i("SignupFragment", "response");
                if (response.code() == 201 || response.code() == 200) {
                    Log.i("SignupFragment", "success");
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //Starting main activity after user sees dialog
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (response.code() == 500) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        Log.e("SignupFragment", jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else
                    Log.e("SignupFragment", response.raw().toString());
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.e("SignupFragment", t.getMessage());

            }

        });
    }

    /**
     * login user
     * @param apiInterface
     * @param email
     * @param password
     */
    public static void loginUser(ApiService apiInterface, final String email, String password) {
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
                    Log.i("token", token);
                }
                    //else   Toast.makeText(context,response.errorBody().string(),Toast.LENGTH_SHORT).show()
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("SignupFragment", t.getMessage());

            }

        });
    }
}