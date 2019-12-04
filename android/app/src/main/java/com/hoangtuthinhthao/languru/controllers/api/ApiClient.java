package com.hoangtuthinhthao.languru.controllers.api;

import android.content.Context;

import com.hoangtuthinhthao.languru.controllers.authentication.SessionControl;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

   // private static final String BASE_URL = "http://10.0.2.2:5001/
    private static final String BASE_URL = "http://184.72.178.217:5001/";

    private static Retrofit retrofit;
    private static SessionControl sessionControl;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHTTPClient().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        if (sessionControl == null)
            sessionControl = new SessionControl(context);
        return retrofit;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHTTPClient().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    private static OkHttpClient.Builder getHTTPClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = "";
                if (sessionControl != null) {
                    token = sessionControl.getJwtToken();
                }
                Request request = chain.request().newBuilder().addHeader("authorization", token).build();
                return chain.proceed(request);
            }
        });
        return builder;
    }
}
