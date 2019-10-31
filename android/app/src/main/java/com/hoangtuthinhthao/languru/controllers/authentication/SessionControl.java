package com.hoangtuthinhthao.languru.controllers.authentication;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionControl {
    public static final String JWT_TOKEN = "JwtToken";
    Context context;
    private  SharedPreferences prefs;

    public SessionControl(Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
    }

    public void setJwtToken(String token) {
        if (token == null || token.equals("")) {
            prefs.edit().remove(JWT_TOKEN).apply();
        } else {
            prefs.edit().putString(JWT_TOKEN, token).apply();
        }
    }

    public  String getJwtToken() {
        String token = prefs.getString(JWT_TOKEN, "");
        if (token == null || token.isEmpty()) {
            token = "";
        }
        return token;
    }

}