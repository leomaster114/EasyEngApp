package com.example.easyengapp.Model;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordResponse {
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private String data;
    @SerializedName("msg")
    private String msg;

    public int getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
}
