package com.example.easyengapp.Model;

public class Result {

    private String topic_id;
    private String user_id;
    private String level_id;
    private int point;
    private boolean passed;
    private String _id;
    public Result() {
    }

    public Result(String topic_id, String user_id, String level_id, int point, boolean pass,String _id) {
        this.topic_id = topic_id;
        this.user_id = user_id;
        this.level_id = level_id;
        this.point = point;
        this.passed = pass;
        this._id = _id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLevel_id() {
        return level_id;
    }

    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isPass() {
        return passed;
    }

    public void setPass(boolean pass) {
        this.passed = pass;
    }
    public boolean getPass(){
        return this.passed;
    }
}
