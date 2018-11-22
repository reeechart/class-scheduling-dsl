package com.dusdus.scheduler;

import java.util.ArrayList;

public class Lecturer {
    private String name;
    private ArrayList<Schedule> availability;
    private LecturerSchedulePreferences preferences;

    public Lecturer(String name) {
        this.name = name;
        this.availability = new ArrayList<>();
    }

    public ArrayList<Schedule> getAvailability() {
        return availability;
    }

    public void addSchedule(Schedule schedule) {
        availability.add(schedule);
    }

    public String getName() {
        return name;
    }

    public void setPreferences(LecturerSchedulePreferences preferences) {
        this.preferences = preferences;
        this.availability = preferences.applyPreferences(availability);
    }

    public LecturerSchedulePreferences getPreferences() {
        return preferences;
    }
}
