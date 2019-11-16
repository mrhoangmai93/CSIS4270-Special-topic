package com.hoangtuthinhthao.languru.controllers.authentication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.hoangtuthinhthao.languru.controllers.api.ApiAuthService;
import com.hoangtuthinhthao.languru.models.Session;
import com.hoangtuthinhthao.languru.models.responses.ResponseLogin;
import com.hoangtuthinhthao.languru.models.User;
import com.hoangtuthinhthao.languru.models.responses.ResponseRegister;

import java.util.List;

import okhttp3.ResponseBody;
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
    public static void signupUser(final Context context, ApiAuthService apiInterface, final User user) {
        // Set up progressbar before call
        Call<ResponseRegister> call = apiInterface.registerUser(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), "android");

        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if(response.isSuccessful()){
                    //response.body(); // have your all data
                    List<Session> sessions = response.body().getSessions();
                    //Log.i("header",response.headers().toString());
                    String token = "";
                    String refreshToken ="";
                    if(sessions != null) {
                        token = sessions.get(sessions.size()-1).getAccessToken();
                        refreshToken = sessions.get(sessions.size()-1).getRefreshToken();
                    }
                    Intent intent = new Intent();
                    intent.setAction(LOGIN_DONE);
                    intent.putExtra("token",token);
                    intent.putExtra("refreshToken",refreshToken);
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
     * @param email
     * @param password
     */
    public static void loginUser(final Context context, ApiAuthService apiInterface , final String email, String password) {
        // Set up progressbar before call
        Call<ResponseLogin> call = apiInterface.loginUser(email, password, "android");

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if(response.isSuccessful()){
                    response.body(); // have your all data
                    List<Session> sessions = response.body().getSessions();
                    String token = "";
                    String refreshToken ="";
                    if(sessions != null) {
                        token = sessions.get(sessions.size()-1).getAccessToken();
                        refreshToken = sessions.get(sessions.size()-1).getRefreshToken();
                    }
                    String fullName = response.body().getFirstName() + " " + response.body().getLastName();
                    Intent intent = new Intent();
                    intent.setAction(LOGIN_DONE);
                    intent.putExtra("token",token);
                    intent.putExtra("refreshToken",refreshToken);
                    intent.putExtra("fullName",  fullName);
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
    public static void logoutUser(final Context context, ApiAuthService apiInterface, final String refreshToken) {
        // Set up progressbar before call
        Call<ResponseBody> call = apiInterface.logoutUser(refreshToken);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Log out",Toast.LENGTH_SHORT).show();
                }
                else   Toast.makeText(context, "Failed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Logout Failed", t.getMessage());
                Intent intent = new Intent();
                intent.setAction(AUTH_FAILED);
                intent.putExtra("message", t.getMessage());
                context.sendBroadcast(intent);
            }

        });
    }
}
