package com.hoangtuthinhthao.languru.controllers.api;

import com.hoangtuthinhthao.languru.models.DefaultResponse;
import com.hoangtuthinhthao.languru.models.RegisterResponse;
import com.hoangtuthinhthao.languru.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


//    @GET("user/login")
//    Call<User> loginUser(@Path("accountId") String accountId);
@FormUrlEncoded
@POST("/v1/user/register")
//    Call<ResponseBody> registerUser( @Body User user);
Call<RegisterResponse> registerUser(
        @Field("email") String email,
        @Field("password") String password,
        @Field("firstName") String firstName,
        @Field("lastName") String lastName
);
}