package com.example.easyengapp.Model;

public class Topic {
    private String _id;
    private int id;
    private String name_topic;

    public Topic() {
    }

    public Topic(String _id, String name_topic, int id) {
        this._id = _id;
        this.id = id;
        this.name_topic = name_topic;
    }

    public String get_id() {
        return _id;
    }

    public int getId() {
        return id;
    }

    public String getName_topic() {
        return name_topic;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName_topic(String name_topic) {
        this.name_topic = name_topic;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "_id='" + _id + '\'' +
                ", id=" + id +
                ", name_topic='" + name_topic + '\'' +
                '}';
    }
}
