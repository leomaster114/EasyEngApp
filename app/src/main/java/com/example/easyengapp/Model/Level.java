package com.example.easyengapp.Model;

public class Level {
    private String _id;
    private String name_level;
    private int id;

    public Level() {
    }

    public Level(String _id, String name_level, int id) {
        this._id = _id;
        this.name_level = name_level;
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName_level() {
        return name_level;
    }

    public void setName_level(String name_level) {
        this.name_level = name_level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
