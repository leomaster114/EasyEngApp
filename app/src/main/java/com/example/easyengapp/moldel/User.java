package com.example.easyengapp.moldel;

import java.util.Date;

public class User {
    private String _id;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Date createAt;
    private Date updateAt;

    public User(String id,String fullname,String username,String password,String email,String avatar) {

        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this._id = id;
    }

    public User(String id,String avatar,String username,String email, String fullname,  String password,Date createAt,Date updateAt) {
        this._id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public User(String email, String username, String password, String fullname) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String pass) {
        this.username = username;
        this.password = pass;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
