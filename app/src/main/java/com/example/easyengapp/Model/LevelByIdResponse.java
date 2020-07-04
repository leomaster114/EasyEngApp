package com.example.easyengapp.Model;

import com.google.gson.annotations.SerializedName;

public class LevelByIdResponse {
    private boolean status;
    @SerializedName("data")
    private Level level;

    public LevelByIdResponse(boolean status, Level level) {
        this.status = status;
        this.level = level;
    }

    public boolean isStatus() {
        return status;
    }

    public Level getLevel() {
        return level;
    }
}
