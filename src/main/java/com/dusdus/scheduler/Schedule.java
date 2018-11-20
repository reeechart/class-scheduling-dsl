package com.dusdus.scheduler;

import java.util.Calendar;

public class Schedule {
    private String start;
    private int hour;
    private int day;

    public Schedule(String start) {
        this.start = start;
        String[] schedule = start.split(",");
        hour = Integer.parseInt(schedule[0]);
        day = Integer.parseInt(schedule[1]);
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public String getStartTimeString() {
        return start;
    }
}
