package com.example.easyengapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Reminder {
    private String _id;
    private boolean active;
    private String content;
    private String time;
    private String user_id;
    private Date createdAt;
    private Date updatedAt;

    public Reminder(String _id, boolean active, String content, String time, String user_id, Date createdAt, Date updatedAt) {
        this._id = _id;
        this.active = active;
        this.content = content;
        this.time = time;
        this.user_id = user_id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
