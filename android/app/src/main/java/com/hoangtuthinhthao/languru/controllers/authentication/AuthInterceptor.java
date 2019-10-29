package com.hoangtuthinhthao.languru.controllers.authentication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        request = request.newBuilder()
//                .addHeader("X-AUTH-TOKEN", ServiceSharedPrefs.getInstance().getToken())
//                .build();
        Response response = chain.proceed(request);
        return response;
    }
}