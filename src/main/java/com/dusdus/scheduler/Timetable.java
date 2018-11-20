package com.dusdus.scheduler;

import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private ArrayList<ArrayList<ArrayList<LectureSchedule>>> timetable;


    public Timetable() {
        timetable = new ArrayList<ArrayList<ArrayList<LectureSchedule>>>();
    }

    public void addLectureSchedule(Integer day, Integer hour, LectureSchedule lectureSchedule) {
        timetable.get(day).get(hour).add(lectureSchedule);
    }

    public ArrayList<LectureSchedule> getLectureSchedule(Integer day, Integer hour) {
        return timetable.get(day).get(hour);
    }

    public void printTimetable() {
        System.out.println("TEST");
    }
}
