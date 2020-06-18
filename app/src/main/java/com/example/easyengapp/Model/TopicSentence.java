package com.example.easyengapp.Model;

public class TopicSentence {
    private int topicId;
    private String topicName;

    public TopicSentence(String topicName) {
        this.topicName = topicName;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }
}
