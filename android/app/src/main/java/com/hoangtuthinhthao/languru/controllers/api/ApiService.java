package com.hoangtuthinhthao.languru.controllers.api;

import com.hoangtuthinhthao.languru.models.DefaultResponse;
import com.hoangtuthinhthao.languru.models.responses.ResponseLogin;
import com.hoangtuthinhthao.languru.models.responses.ResponseRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

//    @GET("user/login")
//    Call<User> loginUser(@Path("accountId") String accountId);
    @FormUrlEncoded
    @POST("/v1/user/register")
    Call<ResponseRegister> registerUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("firstName") String firstName,
            @Field("lastName") String lastName,
            @Field("clientType") String clientType
    );
    @FormUrlEncoded
    @POST("/v1/user/login")
    Call<ResponseLogin> loginUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("clientType") String clientType
    );
}