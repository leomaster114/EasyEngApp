package com.example.easyengapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopicResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private List<Topic> topics;

    public TopicResponse(boolean status, List<Topic> topics) {
        this.status = status;
        this.topics = topics;
    }

    public boolean isStatus() {
        return status;
    }

    public List<Topic> getTopics() {
        return topics;
    }
}
