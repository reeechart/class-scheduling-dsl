package com.dusdus.scheduler;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import javax.sound.midi.SysexMessage;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        try {
            ClassScheduleLexer classScheduleLexer = new ClassScheduleLexer(new ANTLRFileStream("resources/tes.dd"));
            CommonTokenStream tokens = new CommonTokenStream(classScheduleLexer);
            ClassScheduleParser classScheduleParser = new ClassScheduleParser(tokens);

            Timetable timetable = new Timetable();
            classScheduleParser.addParseListener(new ClassScheduleParseTreeListener(timetable));
            classScheduleParser.program();
        } catch (Exception e) {
            // log error
        }
/* //SCHEDULING TEST
        Timetable timetable = new Timetable();
        ArrayList<Lecture> lectures = new ArrayList<Lecture>();
        ArrayList<Classroom> classrooms = new ArrayList<Classroom>();

        Schedule schedule = new Schedule("1,2");
        Schedule schedule2 = new Schedule("1,3");
        Schedule schedule3 = new Schedule("1,4");
//        Schedule schedule4 = new Schedule("1,5");
//        Schedule schedule5 = new Schedule("1,6");

        Lecturer lecturer = new Lecturer("John");
        lecturer.addSchedule(schedule);
        lecturer.addSchedule(schedule2);
        lecturer.addSchedule(schedule3);
//        lecturer.addSchedule(schedule4);
//        lecturer.addSchedule(schedule5);

        Lecturer smith = new Lecturer("Smith");
        smith.addSchedule(schedule);
        smith.addSchedule(schedule2);
        smith.addSchedule(schedule3);

        Lecture lecture = new Lecture("IF4019", 30,2);
        lecture.addFacility("proyektor");
        lecture.setLecturer(lecturer);

        Lecture lecture2 = new Lecture("IF4029", 30,3);
        lecture2.addFacility("proyektor");
        lecture2.setLecturer(smith);

        Constraint const1 = new Constraint("IF4019", "IF4029");
        Classroom classroom = new Classroom("7602", 40);
        classroom.addFacility("proyektor");

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
        Iterator<Lecture> itr = lectures.iterator();
        while(itr.hasNext()) {
            Lecture lec = itr.next();
            scheduler.addLectureSchedule(lec);
        };

        scheduler.schedule();
*/
    }
}
