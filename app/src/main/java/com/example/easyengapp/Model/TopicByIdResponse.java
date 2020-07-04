package com.example.easyengapp.Model;

import com.google.gson.annotations.SerializedName;

public class TopicByIdResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private Topic topic;

    public TopicByIdResponse(boolean status, Topic topic) {
        this.status = status;
        this.topic = topic;
    }

    public boolean isStatus() {
        return status;
    }

    public Topic getTopic() {
        return topic;
    }
}
