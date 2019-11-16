package com.hoangtuthinhthao.languru.controllers.authentication;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionControl {
    public static final String JWT_TOKEN = "JwtToken";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    public static final String FULL_NAME = "FULL_NAME";
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
    public void setRefreshToken(String token) {
        if (token == null || token.equals("")) {
            prefs.edit().remove(REFRESH_TOKEN).apply();
        } else {
            prefs.edit().putString(REFRESH_TOKEN, token).apply();
        }
    }
    public  String getRefreshToken() {
        String token = prefs.getString(REFRESH_TOKEN, "");
        if (token == null || token.isEmpty()) {
            token = "";
        }
        return token;
    }
    public  String getJwtToken() {
        String token = prefs.getString(JWT_TOKEN, "");
        if (token == null || token.isEmpty()) {
            token = "";
        }
        return token;
    }
    public void setFullName(String fullName) {

            prefs.edit().putString(FULL_NAME, fullName).apply();

    }
    public  String getFullName() {
        String fullName = prefs.getString(FULL_NAME, "");
        if (fullName == null || fullName.isEmpty()) {
            fullName = "";
        }
        return fullName;
    }
}