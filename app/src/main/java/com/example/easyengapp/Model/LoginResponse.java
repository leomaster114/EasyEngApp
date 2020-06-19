package com.example.easyengapp.Model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private User user;
    private String code;

    public boolean isStatus(){
        return status;
    }
    public String getMsg(){
        return  msg;
    }

    public User getUser() {
        return user;
    }

    public String getCode() {
        return code;
    }
}
