package com.dusdus.scheduler;

import javax.sound.midi.SysexMessage;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Timetable timetable = new Timetable();
        ArrayList<Lecture> lectures = new ArrayList<>();
        ArrayList<Classroom> classrooms = new ArrayList<>();

        Schedule schedule = new Schedule("1,2");
        Schedule schedule2 = new Schedule("1,3");
//        Schedule schedule3 = new Schedule("1,4");
//        Schedule schedule4 = new Schedule("1,5");
//        Schedule schedule5 = new Schedule("1,6");

        Lecturer lecturer = new Lecturer("John");
        lecturer.addSchedule(schedule);
        lecturer.addSchedule(schedule2);
//        lecturer.addSchedule(schedule3);
//        lecturer.addSchedule(schedule4);
//        lecturer.addSchedule(schedule5);

        Lecture lecture = new Lecture("IF4019", 30,2);
        lecture.addFacility("ac");
        lecture.setLecturer(lecturer);

        Lecture lecture2 = new Lecture("IF4029", 30,3);
        lecture2.addFacility("proyektor");
        lecture2.setLecturer(lecturer);

        Constraint const1 = new Constraint("IF4019", "IF4029");
        Classroom classroom = new Classroom("7602", 40);

        Classroom classroom2 = new Classroom("7603", 40);
        classroom2.addFacility("proyektor");
        classroom2.addFacility("ac");

        classrooms.add(classroom);
        classrooms.add(classroom2);
        lectures.add(lecture);
        lectures.add(lecture2);

        Scheduler scheduler = new Scheduler(timetable, lectures, classrooms);
//
//        System.out.println(classroom.getId());
//        System.out.println(classroom.getCapacity());
//        System.out.println(classroom.getFacilities());
//
//        System.out.println(lecture.getLecturer().getName());
//        Iterator itr = lecturer.getAvailability().iterator();
//        while(itr.hasNext()) {
//            Schedule a = (Schedule)itr.next();
//            a.printSchedule();
//        }
//
//        System.out.println(lecture.getId());
//        System.out.println(lecture.getMaxParticipants());

        scheduler.addLectureSchedule(lecture);
        scheduler.schedule();

    }
}
