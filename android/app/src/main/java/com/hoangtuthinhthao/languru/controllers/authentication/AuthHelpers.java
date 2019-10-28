package com.hoangtuthinhthao.languru.controllers.authentication;

import android.util.Log;

import com.google.gson.Gson;
import com.hoangtuthinhthao.languru.controllers.api.ApiService;
import com.hoangtuthinhthao.languru.models.DefaultResponse;
import com.hoangtuthinhthao.languru.models.RegisterResponse;
import com.hoangtuthinhthao.languru.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthHelpers {

    public static void signupUser(ApiService apiInterface, final User user) {
        // Set up progressbar before call
        Log.i("SignupFragment", "run register");
        Call<RegisterResponse> call = apiInterface.registerUser(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName());

        final Gson gson = new Gson();
        final String json = gson.toJson(user);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.i("SignupFragment", "response");
                if (response.code() == 201) {
                    Log.i("SignupFragment", "success");
//                    try {
//                       // JSONObject jsonObject = new JSONObject(response.body().string());
//                        //Starting main activity after user sees dialog
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
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
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("SignupFragment", "failed");
                Log.e("SignupFragment", t.getMessage());
                Log.e("SignupFragment", t.getLocalizedMessage());
            }

        });
    }
}
