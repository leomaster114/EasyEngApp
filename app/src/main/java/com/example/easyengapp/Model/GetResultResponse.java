package com.example.easyengapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetResultResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private List<Result> results;

    public GetResultResponse(boolean status, List<Result> results) {
        this.status = status;
        this.results = results;
    }

    public boolean isStatus() {
        return status;
    }

    public List<Result> getResults() {
        return results;
    }
}
