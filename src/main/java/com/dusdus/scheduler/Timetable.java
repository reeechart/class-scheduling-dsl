package com.dusdus.scheduler;

import javax.lang.model.type.IntersectionType;
import java.util.ArrayList;
import java.util.List;

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

    public Integer schedule( LectureSchedule lectureSchedule, Integer currentClassroom,
                                         Integer counter, ConflictingConstraint constraint) {

        ArrayList<Schedule> settedSchedule = new ArrayList<>();
        System.out.println(String.format("COUNTER: %d", counter));
        ArrayList<Schedule> scheduleList = lectureSchedule.getLecture().getLecturer().getAvailability();

        System.out.println(String.format("CLASSROOM: %s", lectureSchedule.getClassroom(currentClassroom).getId()));

        // Set classroom according to availability and constraints
        iterateSchedule: {
            for(int i = 0; i < scheduleList.size(); i++) {
                Schedule current = scheduleList.get(i);
                current.printSchedule();

                // Iterate through setted lecture in available schedule
                Integer allocatedLectureSize = timetable.get(current.getDay()).get(current.getHour()).size();
                boolean conflict = false;

                for (int j = 0; j < allocatedLectureSize; j++) {
                    System.out.println(String.format("Allocated Lecture index: %d", j));
                    LectureSchedule settedLecture = timetable.get(current.getDay()).get(current.getHour()).get(j);

                    // Check if classroom is used
                    if (settedLecture.getAllocatedClassroom().getId()
                            .equals(lectureSchedule.getClassroom(currentClassroom).getId())) {
                        System.out.println(String.format("ALLOCATED: %s",settedLecture.getAllocatedClassroom().getId()));
                        System.out.println(String.format("CUrrent: %s",lectureSchedule.getClassroom(currentClassroom).getId()));
                        conflict = true;
                    }

                    // Check for constraints
                    if (constraint != null) {
                        String conflictingLecture = constraint.getValue(lectureSchedule.getLecture().getId());
                        System.out.println(String.format("CONSTRAINTS: %s, %s", conflictingLecture,
                                settedLecture.getAllocatedClassroom().getId()));
                        if (conflictingLecture.equals(settedLecture.getAllocatedClassroom().getId())) {
                            conflict = true;
                        }
                    }
                }

                if (!conflict) {
                    // Set lecture to timetable and allocate class
                    counter--;
                    System.out.println(String.format("COUNTER: %d", counter));
                    lectureSchedule.setAllocatedClassroom(lectureSchedule.getClassroom(currentClassroom));
                    LectureSchedule newLectureSchedule = new LectureSchedule(lectureSchedule);
                    setLectureSchedule(current.getDay(), current.getHour(), newLectureSchedule);
                    settedSchedule.add(current);
                    if (counter == 0) {
                        return 0;
                    }
                }
            }
        }


        // Check if all credits is setted
        if (counter > 0) {
            System.out.println(String.format("Setted SIZE: %d", settedSchedule.size()));

            //Recursively search for available slot
            Integer nextClassroom = currentClassroom + 1;
            if(nextClassroom < lectureSchedule.getAvailableClassroom().size()) {
                counter = schedule(lectureSchedule, nextClassroom, counter, constraint);
            }

        }

        // Remove setted if after recursive not all are allocated
        if (counter > 0) {
            // Remove all allocated timeslot if no available time
            for (int i = 0; i < settedSchedule.size(); i++) {
                Schedule schedule = settedSchedule.get(i);
                settedSchedule.remove(i);
                ArrayList<LectureSchedule> lectureList = timetable.get(schedule.getDay()).get((schedule.getHour()));
                lectureList.remove(lectureList.size()-1);
            }
        }

        return counter;

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
                System.out.printf("]\n");
            }
        }
    }
}
