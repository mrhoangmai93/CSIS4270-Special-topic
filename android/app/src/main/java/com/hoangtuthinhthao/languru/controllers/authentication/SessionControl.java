package com.hoangtuthinhthao.languru.controllers.authentication;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionControl {
    Context context;
    private  SharedPreferences prefs;

    public SessionControl(Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
    }

    public void setJwtToken(String token) {
        prefs.edit().putString("JwtToken", token).apply();
    }

    public  String getJwtToken() {
        String token = prefs.getString("JwtToken", "");
        if (token == null || token.isEmpty()) {
            token = "";
        }
        return token;
    }

}