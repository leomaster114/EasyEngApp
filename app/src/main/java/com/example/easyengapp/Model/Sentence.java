package com.example.easyengapp.Model;

public class Sentence {
    private int sentence_id;
    private String content;
    private String mean;
//    private int topicId;

    public Sentence(String content, String mean) {
        this.content = content;
        this.mean = mean;
//        this.topicId = topicId;
    }

    public int getSentence_id() {
        return sentence_id;
    }

    public String getContent() {
        return content;
    }

    public String getMean() {
        return mean;
    }

//    public int getTopicId() {
//        return topicId;
//    }
}
