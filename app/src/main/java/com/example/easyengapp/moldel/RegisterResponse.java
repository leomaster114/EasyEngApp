package com.example.easyengapp.moldel;

public class RegisterResponse {
    private boolean status;
    private String msg;
    private User user;

    public boolean isStatus(){
        return status;
    }
    public String getMsg(){
        return msg;
    }
    public User getUser(){
        return user;
    }
}
