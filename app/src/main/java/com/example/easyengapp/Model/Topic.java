package com.example.easyengapp.Model;

public class Topic {
    private int topicId;
    private String topicName;

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }
}
