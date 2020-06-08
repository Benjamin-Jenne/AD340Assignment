package com.example.helloworld2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Settings {
    @PrimaryKey
    public int id;
    @NonNull
    private int minAge = 18;

    @ColumnInfo
    private int maxAge = 18;

    @ColumnInfo
    private int range = 1;

    @ColumnInfo
    private boolean isPrivate = false;

    @ColumnInfo
    private boolean dailyReminder = false;

    @ColumnInfo
    private int reminderHour = 1;

    @ColumnInfo
    private int reminderMinutes = 1;

    public Settings(){};

    public int getMinAge() { return minAge; }

    public int getMaxAge() { return maxAge; }

    public int getRange() { return range; }

    public boolean getIsPrivate() {return isPrivate; }

    public boolean getDailyReminder() {return dailyReminder; }

    public int getReminderHour() { return reminderHour; }

    public int getReminderMinutes() { return reminderMinutes; }

    public void setMinAge(int min) { this.minAge = min; }

    public void setMaxAge(int max) { this.maxAge = max; }

    public void setRange(int r) { this.range = r; }

    public void setIsPrivate(boolean bool) {this.isPrivate = bool; }

    public void setDailyReminder(boolean bool) { this.dailyReminder = bool; }

    public void setReminderHour(int rh) { this.reminderHour = rh; }

    public void setReminderMinutes(int rm) { this.reminderMinutes = rm; }

}
