package com.hoangtuthinhthao.languru.controllers.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://10.0.2.2:5001/";
    private static Retrofit retrofit;

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
        return builder;
    }
}
