package com.example.easyengapp.moldel;

import com.google.gson.annotations.SerializedName;

public class UpdateUserResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private User user;

    public boolean isStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }
}
