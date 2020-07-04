package com.example.easyengapp.Model;

import com.google.gson.annotations.SerializedName;

public class UserReminderUpdateResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("msg")
    private String msg;

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
