package com.kominfo.anaksehat.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.kominfo.anaksehat.models.User;


/**
 * Created by emrekabir on 12/20/17.
 */

public class AppSession {

    public final static String SHAREDKEY = "Stunting-session";
    public final static String TOKEN = "token";
    public final static String USER = "username";
    public final static String MOTHER = "mother";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public AppSession(Context context) {
        sharedPreferences = context.getSharedPreferences(SHAREDKEY, Context.MODE_PRIVATE);
    }

    public void clearSession() {
        editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    public void logout(){
        editor = sharedPreferences.edit();
        editor.remove(TOKEN);
        editor.remove(USER);
        editor.remove(MOTHER);
        editor.clear();
        editor.apply();
    }

    public String getData(String data){
        return sharedPreferences.getString(data, null);
    }

    public String getData(String data, String default_value){
        return sharedPreferences.getString(data, default_value);
    }

    public void setData(String data, String value){
        editor = sharedPreferences.edit();
        editor.putString(data, value);
        editor.apply();
    }

    public void deleteData(String data){
        editor = sharedPreferences.edit();
        editor.remove(data);
        editor.apply();
    }

    public void createSession(String token, User user){
        editor = sharedPreferences.edit();
        editor.putString(TOKEN, token);
        editor.putString(USER, new Gson().toJson(user));
        editor.apply();
    }

    public boolean isShowedGuide(String data){
        return sharedPreferences.getBoolean(data, false);
    }

    public void setShowGuide(String data){
        editor = sharedPreferences.edit();
        editor.putBoolean(data, true);
        editor.apply();
    }

    public boolean isLogin() {
        return (sharedPreferences.getString(TOKEN, null) != null);
    }

}
