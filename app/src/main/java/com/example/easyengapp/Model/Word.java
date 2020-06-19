package com.example.easyengapp.Model;

public class Word {
    private int word_id;
    private String word;
    private String phonetic;
    private String mean;
    private String value;
    private Topic topic;

    public Word() {
    }

    public Word(String word, String phonetic, String sinpleMeaning, String content, Topic topic) {
        this.word = word;
        this.phonetic = phonetic;
        this.mean = sinpleMeaning;
        this.value = content;
        this.topic = topic;
    }

    public void setWord_id(int word_id) {
        this.word_id = word_id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public void setMean(String sinpleMeaning) {
        this.mean = sinpleMeaning;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public int getWord_id() {
        return word_id;
    }

    public String getWord() {
        return word;
    }

    public String getPhonetic() {
        return phonetic;
    }


    public String getMean() {
        return mean;
    }


    public String getValue() {
        return value;
    }

    public Topic getTopic() {
        return topic;
    }
}
