package com.example.easyengapp.Model;

public class Word {
    private int word_id;
    private String word;
    private String phonetic;
    private String sinpleMeaning;
    private String value;
//    private int topicId;

    public Word(String word, String phonetic, String sinpleMeaning, String content) {
        this.word = word;
        this.phonetic = phonetic;
        this.sinpleMeaning = sinpleMeaning;
        this.value = content;
//        this.topicId = topicId;
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


    public String getSinpleMeaning() {
        return sinpleMeaning;
    }


    public String getValue() {
        return value;
    }

//    public int getTopicId() {
//        return topicId;
//    }
}
