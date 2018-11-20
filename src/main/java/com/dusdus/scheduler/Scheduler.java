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

    public Scheduler(Timetable timetable, ArrayList<Lecture> lectures, ArrayList<Classroom> classrooms) {
        this.timetable = timetable;
        this.lectures = lectures;
        this.classrooms = classrooms;
        this.lectureScheduleArrayList = new ArrayList<>();
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

        return result;
    }

    public void addLectureSchedule(Lecture lecture) {
        ArrayList<Classroom> result = findClassrooms(lecture);
        LectureSchedule lectureSchedule = new LectureSchedule(result, lecture);
        lectureScheduleArrayList.add(lectureSchedule);
    }

    public void setLectureScheduleToTimetable(LectureSchedule lectureSchedule) {
        timetable.schedule(lectureSchedule,0);
    }

    public void schedule() {
        System.out.println(lectureScheduleArrayList.size());
        for (int i = 0; i < lectureScheduleArrayList.size(); i++) {
            setLectureScheduleToTimetable(lectureScheduleArrayList.get(i));
        }
        timetable.printTimetable();
    }



}
