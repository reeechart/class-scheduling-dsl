package com.dusdus.scheduler;

public class LectureSchedule {
    private Lecture lecture;
    private Classroom classroom;

    public LectureSchedule(Classroom classroom, Lecture lecture) {
        this.classroom = classroom;
        this.lecture = lecture;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public Lecture getLecture() {
        return lecture;
    }
}
