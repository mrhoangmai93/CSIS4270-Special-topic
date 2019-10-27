package com.hoangtuthinhthao.languru.controllers.api;

import com.hoangtuthinhthao.languru.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    String URL = "https://localhost:5001/v1/";

//    @GET("user/login")
//    Call<User> loginUser(@Path("accountId") String accountId);
    @POST("/api/users/register")
    Call<ResponseBody> registerUser(@Header("Authorization") String token , @Body User user);
}