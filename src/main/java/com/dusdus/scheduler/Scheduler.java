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
    private ClassroomPreferences classroomPreferences;

    public Scheduler(Timetable timetable, ArrayList<Lecture> lectures, ArrayList<Classroom> classrooms) {
        this.timetable = timetable;
        this.lectures = lectures;
        this.classrooms = classrooms;
        this.lectureScheduleArrayList = new ArrayList<>();
    }

    public void addConflictingConstraint(ConflictingConstraint constraint) {
        this.constraints = constraint;
    }

    public void addClassroomPreferences(ClassroomPreferences preferences) {
        this.classroomPreferences = preferences;
    }

    public ArrayList<Classroom> findClassrooms(Lecture lecture) {
        ArrayList<Classroom> result = new ArrayList<>();
        for (Classroom classroom: classrooms) {
            boolean satisfiedCondition = true;
            // Check Capacity
            if (classroom.getCapacity() < lecture.getMaxParticipants()) {
                satisfiedCondition = false;
            }

            // Check facilities
            for (String fac : lecture.getFacilities()) {
                if (!classroom.getFacilities().contains(fac)) {
                    satisfiedCondition = false;
                }
            }

            if (satisfiedCondition) {
                result.add(classroom);
            }
        }

        if (classroomPreferences != null) {
            result = classroomPreferences.applyPreferences(result, lecture);
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
