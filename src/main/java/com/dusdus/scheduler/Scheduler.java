package com.dusdus.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Scheduler {

    private Timetable timetable;
    private ArrayList<Lecture> lectures;
    private ArrayList<Classroom> classrooms;
    private ArrayList<LectureSchedule> lectureScheduleArrayList;
    private ConflictingConstraint constraints;

    public Scheduler(Timetable timetable, ArrayList<Lecture> lectures, ArrayList<Classroom> classrooms) {
        this.timetable = timetable;
        this.lectures = lectures;
        this.classrooms = classrooms;
        this.lectureScheduleArrayList = new ArrayList<>();

        for(Lecture lecture: lectures) {
            addLectureSchedule(lecture);
        }
    }

    public void addConflictingConstraint(ConflictingConstraint constraint) {
        this.constraints = constraint;
    }

    public ArrayList<Classroom> findClassrooms(Lecture lecture) {
        ArrayList<Classroom> result = new ArrayList<>();
        Iterator itr = classrooms.iterator();
        while(itr.hasNext()) {
            boolean satisfiedCondition = true;
            Classroom classroom = (Classroom)itr.next();

            // Check Capacity
            if (classroom.getCapacity() < lecture.getMaxParticipants()) {
                satisfiedCondition = false;
            }

            // Check facilities
            Iterator<String> facility = lecture.getFacilities().iterator();
            while (facility.hasNext()) {
                String fac = facility.next();
                if (!classroom.getFacilities().contains(fac)) {
                    satisfiedCondition = false;
                }
            }

            if (satisfiedCondition) {
                result.add(classroom);
            }
        }
        if (result.size() == 0) {
            System.out.println(String.format("Tidak terdapat ruang kelas yang memenuhi kebutuhan kelas %s",
                    lecture.getId()));
            System.exit(0);
        }
        return result;
    }

    public void addLectureSchedule(Lecture lecture) {
        ArrayList<Classroom> result = findClassrooms(lecture);
        LectureSchedule lectureSchedule = new LectureSchedule(result, lecture);
        lectureScheduleArrayList.add(lectureSchedule);
        System.out.println(String.format("SIZE: %d ", lectureScheduleArrayList.size()));
    }

    public void setLectureScheduleToTimetable(LectureSchedule lectureSchedule) {
        timetable.schedule(lectureSchedule,0, new ArrayList<Schedule>(), constraints);
    }

    public void schedule() {
        for (int i = 0; i < lectureScheduleArrayList.size(); i++) {
            System.out.println(String.format("INDEX: %d", i));
            setLectureScheduleToTimetable(lectureScheduleArrayList.get(i));
        }
        timetable.printTimetable();
    }



}
