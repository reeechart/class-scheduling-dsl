package com.dusdus.scheduler;

import java.util.ArrayList;

public class Lecturer {
    private String name;
    private ArrayList<Schedule> availability;

    public Lecturer(String name) {
        this.name = name;
        this.availability = new ArrayList<Schedule>();
    }

    public void addSchedule(Schedule schedule) {
        availability.add(schedule);
    }

    public String getName() {
        return name;
    }
}
