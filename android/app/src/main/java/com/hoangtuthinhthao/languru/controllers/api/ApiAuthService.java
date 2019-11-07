package com.hoangtuthinhthao.languru.controllers.api;

import com.hoangtuthinhthao.languru.models.responses.Progress;
import com.hoangtuthinhthao.languru.models.responses.ResponseLogin;
import com.hoangtuthinhthao.languru.models.responses.ResponseRegister;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiAuthService {


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
    @FormUrlEncoded
    @PUT("/v1/user/logout")
    Call<ResponseBody> logoutUser(
            @Field("refreshToken") String refreshToken
    );

    @GET("/v1/user/getProgress")
    Call<ArrayList<Progress>> getProgress();
}