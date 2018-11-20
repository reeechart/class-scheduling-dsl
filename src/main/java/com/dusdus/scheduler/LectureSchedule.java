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

    public Classroom getClassroom(Integer idx) {
        return availableClassroom.get(idx);
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
        System.out.printf("Lecture ID: %s \n", lecture.getId());

    }

}
