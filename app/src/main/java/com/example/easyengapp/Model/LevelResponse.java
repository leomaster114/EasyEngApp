package com.example.easyengapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LevelResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private List<Level> levelList;

    public LevelResponse(boolean status, List<Level> levelList) {
        this.status = status;
        this.levelList = levelList;
    }

    public boolean isStatus() {
        return status;
    }

    public List<Level> getLevelList() {
        return levelList;
    }
}
