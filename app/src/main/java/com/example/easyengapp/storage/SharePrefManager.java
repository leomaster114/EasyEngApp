package com.example.easyengapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.easyengapp.Model.User;

public class SharePrefManager {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static SharePrefManager mInstance;
    private Context mContext;

    private SharePrefManager(Context context) {
        mContext = context;
    }

    public static synchronized SharePrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharePrefManager(context);
        }
        return mInstance;
    }

    // lưu user vào sharedpref để k phải đăng nhập lại
    public void saveUser(User user) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("_id", user.getId());
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());
        editor.putString("fullname", user.getFullname());
        editor.putString("email", user.getEmail());
        editor.putString("avatar",user.getAvatar());

        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("_id", "-1") != "-1";
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString("_id", "-1"),
                sharedPreferences.getString("fullname", "null"),
                sharedPreferences.getString("username", "null"),
                sharedPreferences.getString("password", "null"),
                sharedPreferences.getString("email", "null"),
                sharedPreferences.getString("avatar", "null")
        );
    }
    // save isFirstTime run app
    public void saveIsFirstTime(boolean isFirstTime){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FistTime",isFirstTime);
        editor.apply();
    }
    public boolean getFistTime(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("FistTime",true);
    }
    public void clear(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }
}
