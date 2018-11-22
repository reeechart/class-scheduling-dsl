package com.dusdus.scheduler;

import java.util.ArrayList;

public class LectureSchedule {

    private Lecture lecture;
    private ArrayList<Classroom> availableClassroom;
    private Classroom allocatedClassroom;

    public LectureSchedule(ArrayList<Classroom> classrooms, Lecture lecture) {
        this.availableClassroom = classrooms;
        this.lecture = lecture;
    }

    public LectureSchedule(LectureSchedule lectureSchedule) {
        this.availableClassroom = lectureSchedule.availableClassroom;
        this.lecture = lectureSchedule.lecture;
        this.allocatedClassroom = lectureSchedule.allocatedClassroom;
    }

    public Classroom getClassroom(Integer idx) {
        Classroom result = new Classroom("0",0);
        if (availableClassroom != null && availableClassroom.size() > 0) {
            result = availableClassroom.get(idx);
        }
        return  result;
    }

    public ArrayList<Classroom> getAvailableClassroom() {
        return availableClassroom;
    }

    public Classroom getAllocatedClassroom() {
        return allocatedClassroom;
    }

    public void setAllocatedClassroom(Classroom allocatedClassroom) {
        this.allocatedClassroom = allocatedClassroom;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void printLectureSchedule() {
        System.out.printf("Lecture ID: %s - Classroom: %s \n", lecture.getId(),allocatedClassroom.getId());

    }

}
