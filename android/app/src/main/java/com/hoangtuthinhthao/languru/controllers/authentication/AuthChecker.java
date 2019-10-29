package com.hoangtuthinhthao.languru.controllers.authentication;

import android.content.Context;

import com.auth0.android.jwt.JWT;

import java.util.Date;

public class AuthChecker {

    private Context context;
    private SessionControl sessionControl;

    public AuthChecker(SessionControl sessionControl) {
        this.sessionControl = sessionControl;
    }

    public boolean isAuthenticated() {
        boolean isAuth = false;

        String token = sessionControl.getJwtToken();

        if(!token.equals("")) {
            JWT jwt = new JWT(token);

            Date expiresAt = jwt.getExpiresAt();

            if (expiresAt != null && System.currentTimeMillis() < expiresAt.getTime()) {
                isAuth = true;
            }
        }


        return isAuth;
    }
}
