package com.example.easyengapp.moldel;

import com.google.gson.annotations.SerializedName;

public class UpdateAvatarResponse {
    @SerializedName("image")
    private String image;
    @SerializedName("location")
    private String location;
    @SerializedName("error")
    private String err;

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public String getError() {
        return err;
    }

}
