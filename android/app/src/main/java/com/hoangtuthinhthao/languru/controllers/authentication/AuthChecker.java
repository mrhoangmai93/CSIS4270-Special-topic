package com.hoangtuthinhthao.languru.controllers.authentication;

import android.content.Context;

import com.auth0.android.jwt.JWT;

import java.util.Date;

public class AuthChecker {

    private Context context;
    private SessionControl sessionControl;
    private JWT jwt;

    public AuthChecker(SessionControl sessionControl) {
        this.sessionControl = sessionControl;
        String token = sessionControl.getJwtToken();

        if(!(token == null  || token.equals(""))) {
            this.jwt = new JWT(token);
        }

    }

    public boolean isAuthenticated() {

        return jwt != null && jwt.isExpired(0);
    }


}
