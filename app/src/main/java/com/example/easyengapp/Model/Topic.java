package com.example.easyengapp.Model;

public class Topic {
    private int topicId;
    private String topicName;

    public Topic() {
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Topic(int topicId, String topicName) {
        this.topicId = topicId;
        this.topicName = topicName;
    }

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public int getTopicId() {
        return this.topicId;
    }

    public String getTopicName() {
        return this.topicName;
    }
}
