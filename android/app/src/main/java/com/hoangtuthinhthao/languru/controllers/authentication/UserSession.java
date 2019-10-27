package com.hoangtuthinhthao.languru.controllers.authentication;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserSession {
    Context context;
    private SharedPreferences prefs;

    public UserSession(Context cntx) {
        this.context = cntx;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setJwtToken(String token) {
        prefs.edit().putString("JwtToken", token).commit();
    }

    public String getJwtToken() {
        String token = prefs.getString("JwtToken", "");
        if (token == null || token.isEmpty()) {
            token = "";
        }
        return token;
    }

}