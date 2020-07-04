package com.example.easyengapp.Model;

public class SaveResultResponse {
    private boolean status;
    private String msg;

    public SaveResultResponse(boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
