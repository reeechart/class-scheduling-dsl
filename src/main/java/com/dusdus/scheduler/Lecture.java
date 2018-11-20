package com.dusdus.scheduler;

import java.util.ArrayList;

public class Lecture {
    // Class requirements
    private String id;
    private Lecturer lecturer;
    private ArrayList<String> facilities;
    private Integer maxParticipants;
    private Integer credits;

    public Lecture(String id, Integer maxParticipants) {
        this.facilities = new ArrayList<>();
        this.id = id;
        this.maxParticipants = maxParticipants;
    }

    public String getId() {
        return id;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public ArrayList<String> getFacilities() {
        return facilities;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public void addFacility(String facility) {
        this.facilities.add(facility);
    }

    public void delFacilities() {
        this.facilities = new ArrayList<>();
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}
