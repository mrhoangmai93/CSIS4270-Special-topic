package com.hoangtuthinhthao.languru.controllers.authentication;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    Context context;
    private SharedPreferences prefs;

    public Session(Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
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