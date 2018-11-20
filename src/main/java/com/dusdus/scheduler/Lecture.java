package com.dusdus.scheduler;

import java.util.ArrayList;

public class Lecture {
    // Class requirements
    private String id;
    private Lecturer lecturer;
    private ArrayList<LectureSchedule> lectureSchedules;
    private ArrayList<String> facilities;
    private int maxParticipants;

    public Lecturer getLecturer() {
        return lecturer;
    }

    public ArrayList<LectureSchedule> getLectureSchedules() {
        return lectureSchedules;
    }

    public ArrayList<String> getFacilities() {
        return facilities;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }
}
