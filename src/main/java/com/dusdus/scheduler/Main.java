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
        Schedule schedule3 = new Schedule("1,4");
        Schedule schedule4 = new Schedule("1,5");
        Schedule schedule5 = new Schedule("1,6");

        Lecturer john = new Lecturer("John");
        john.addSchedule(schedule);
        john.addSchedule(schedule2);
        john.addSchedule(schedule3);

        Lecturer smith = new Lecturer("Smith");
        smith.addSchedule(schedule);
        smith.addSchedule(schedule2);
        smith.addSchedule(schedule3);
        smith.addSchedule(schedule4);
        smith.addSchedule(schedule5);

        Lecturer doe = new Lecturer("Doe)");
        doe.addSchedule(schedule2);
        doe.addSchedule(schedule3);

        Lecture lecture = new Lecture("IF4019", 30,2);
        lecture.addFacility("proyektor");
        lecture.setLecturer(john);

        Lecture lecture2 = new Lecture("IF4029", 30,3);
        lecture2.addFacility("proyektor");
        lecture2.setLecturer(smith);

        Lecture if4012 = new Lecture("IF4012", 30, 2);
        if4012.addFacility("ac");
        if4012.setLecturer(doe);

        ConflictingConstraint const1 = new ConflictingConstraint();
        const1.addKeyValue("IF4019", "IF4029");
        const1.addKeyValue("IF4029", "IF4019");

        Classroom classroom = new Classroom("7602", 40);
        classroom.addFacility("proyektor");

        Classroom classroom2 = new Classroom("7603", 40);
        classroom2.addFacility("proyektor");
        classroom2.addFacility("ac");

        Classroom classroom3 = new Classroom("7604", 40);
        classroom3.addFacility("proyektor");
        classroom3.addFacility("ac");


        classrooms.add(classroom);
        classrooms.add(classroom2);
        classrooms.add(classroom3);
        lectures.add(lecture);
        lectures.add(lecture2);
        lectures.add(if4012);

        Scheduler scheduler = new Scheduler(timetable, lectures, classrooms);
        scheduler.addConflictingConstraint(const1);
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
        Iterator<Lecture> itr = lectures.iterator();
        while(itr.hasNext()) {
            Lecture lec = itr.next();
            scheduler.addLectureSchedule(lec);
        };

        scheduler.schedule();

    }
}
