package com.dusdus.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Scheduler {

    private Timetable timetable;
    private ArrayList<Lecture> lectures;
    private ArrayList<Classroom> classrooms;
    private Map<String, LectureSchedule> lectureScheduleMap;

    public Scheduler(Timetable timetable, ArrayList<Lecture> lectures, ArrayList<Classroom> classrooms) {
        this.timetable = timetable;
        this.lectures = lectures;
        this.classrooms = classrooms;
        this.lectureScheduleMap = new HashMap<String, LectureSchedule>();
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

    public void setLectureSchedule(Lecture lecture) {
        ArrayList<Classroom> result = findClassrooms(lecture);
        LectureSchedule lectureSchedule = new LectureSchedule(result.get(0), lecture);
        lectureScheduleMap.put(lecture.getId(), lectureSchedule);
    }



}
