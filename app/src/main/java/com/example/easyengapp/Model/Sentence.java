package com.example.easyengapp.Model;

public class Sentence {
    private int sentence_id;
    private String content;
    private String mean;
    private Topic topic;

    public Sentence() {
    }

    public Sentence(String content, String mean, Topic topic) {
        this.content = content;
        this.mean = mean;
        this.topic = topic;
    }

    public void setSentence_id(int sentence_id) {
        this.sentence_id = sentence_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
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

    public Topic getTopic() {
        return topic;
    }
}
