package com.dusdus.scheduler;

public class LectureSchedule {
    private Classroom classroom;
    private Schedule schedule;

    public LectureSchedule(Classroom classroom, Schedule schedule) {
        this.classroom = classroom;
        this.schedule = schedule;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
