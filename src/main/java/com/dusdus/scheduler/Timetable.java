package com.dusdus.scheduler;

import java.util.ArrayList;

public class Timetable {
    private ArrayList<ArrayList<ArrayList<LectureSchedule>>> timetable;

    private final static Integer DAYS = 5;
    private final static Integer TOTAL_TIME = 11;


    public Timetable() {
        timetable = new ArrayList<>(DAYS);
        for (int i = 0; i < DAYS; i++) {
            timetable.add(new ArrayList<>(TOTAL_TIME));
            for (int j = 0; j < TOTAL_TIME; j++) {
                timetable.get(i).add(new ArrayList<>());
            }
        }
    }

    public void setLectureSchedule(Integer day, Integer hour, LectureSchedule lectureSchedule) {
        timetable.get(day).get(hour).add(lectureSchedule);
    }

    public ArrayList<Schedule> schedule( LectureSchedule lectureSchedule, Integer currentClassroom,
                                         ArrayList<Schedule> settedSchedule, ConflictingConstraint constraint) {

        ArrayList<Schedule> scheduleList = lectureSchedule.getLecture().getLecturer().getAvailability();

        // Set classroom according to availability and constraints
        for (int i = 0; i < scheduleList.size(); i++) {
            boolean isSetted = false;
            Schedule current = scheduleList.get(i);

            // Check if allocated before
            for (int settedIdx = 0; settedIdx < settedSchedule.size(); settedIdx++) {
                if (settedSchedule.get(settedIdx).compareSchedule(current)) {
                    isSetted = true;
                }
            }

            if (isSetted) {continue;}

            // Iterate through setted lecture in available schedule
            Integer allocatedLectureSize = timetable.get(current.getDay()).get(current.getTime()).size();
            boolean conflict = false;

            for (int j = 0; j < allocatedLectureSize; j++) {
                LectureSchedule settedLecture = timetable.get(current.getDay()).get(current.getTime()).get(j);

                // Check if classroom is used
                if (settedLecture.getAllocatedClassroom().getId()
                        .equals(lectureSchedule.getClassroom(currentClassroom).getId())) {
                    conflict = true;
                }

                // Check for constraints
                if (constraint.getValueList(lectureSchedule.getLecture().getId()) != null) {
                    ArrayList<String> conflictingLecture = constraint.getValueList(lectureSchedule.getLecture().getId());

                    if (conflictingLecture.contains(settedLecture.getLecture().getId())) {
                        conflict = true;
                    }
                }
            }

            if (!conflict) {
                // Set lecture to timetable and allocate class
                lectureSchedule.setAllocatedClassroom(lectureSchedule.getClassroom(currentClassroom));
                LectureSchedule newLectureSchedule = new LectureSchedule(lectureSchedule);
                setLectureSchedule(current.getDay(), current.getTime(), newLectureSchedule);
                settedSchedule.add(current);
                if (settedSchedule.size() == lectureSchedule.getLecture().getCredits()) {
                    return settedSchedule;
                }
            }
        }

        // Check if all credits is setted
        if (settedSchedule.size() < lectureSchedule.getLecture().getCredits()) {
            //Recursively search for available slot
            Integer nextClassroom = currentClassroom + 1;
            if(nextClassroom < lectureSchedule.getAvailableClassroom().size()) {
                settedSchedule = schedule(lectureSchedule, nextClassroom, settedSchedule, constraint);
            }

        }

        // Remove setted if after recursive not all are allocated
        if (settedSchedule.size() < lectureSchedule.getLecture().getCredits()) {
            // Remove all allocated timeslot if no available time
            for (int i = 0; i < settedSchedule.size(); i++) {
                Schedule schedule = settedSchedule.get(i);
                settedSchedule.remove(i);
                ArrayList<LectureSchedule> lectureList = timetable.get(schedule.getDay()).get((schedule.getTime()));
                lectureList.remove(lectureList.size()-1);
            }
        }

        return settedSchedule;

    }

    public ArrayList<LectureSchedule> getLectureSchedule(Integer day, Integer hour) {
        return timetable.get(day).get(hour);
    }

    public void printTimetable() {
        for (int day = 0; day < DAYS; day++) {
            for (int time = 0; time < TOTAL_TIME; time++) {
                System.out.println(String.format("Day %s - Time %s: [", day, time));
                for (int i = 0; i < timetable.get(day).get(time).size(); i++) {
                    LectureSchedule current = timetable.get(day).get(time).get(i);
                    System.out.println(String.format("(%s-%s, %s),", current.getLecture().getId(),
                            current.getLecture().getLecturer().getName(), current.getAllocatedClassroom().getId()));
                }
                System.out.print("]\n");
            }
        }
    }
}
