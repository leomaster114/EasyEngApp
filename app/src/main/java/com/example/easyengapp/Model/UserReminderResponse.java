package com.example.easyengapp.Model;

import com.example.easyengapp.Model.Reminder;
import com.google.gson.annotations.SerializedName;

public class UserReminderResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private Reminder reminder;

    public boolean isStatus() {
        return status;
    }

    public Reminder getReminder() {
        return reminder;
    }
}
