package com.dusdus.scheduler;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassScheduleParseTreeListener extends ClassScheduleBaseListener {
    private Timetable timetable;
    private ArrayList<Lecture> lectures;
    private ArrayList<Classroom> classrooms;
    private int warningCount = 0;

    public ClassScheduleParseTreeListener(Timetable timetable) {
        this.timetable = timetable;
        lectures = new ArrayList<Lecture>();
        classrooms = new ArrayList<Classroom>();
    }

    public void enterProgram(ClassScheduleParser.ProgramContext ctx) {
        System.out.println("Enter Program");
    }

    @Override
    public void exitCreate_classroom(ClassScheduleParser.Create_classroomContext ctx) {
        // System.out.println("Classroom ID:" + ctx.classroom_id().CLASSROOM_ID());
        // System.out.println("Classroom Capacity " + ctx.capacity().NUM());
        String classroomID = ctx.classroom_id().CLASSROOM_ID().toString();
        int classCapacity = Integer.parseInt(ctx.capacity().NUM().toString());
        int idx = searchClassroom(classroomID);
        if(idx != -1) {
            warningCount++;
            System.out.println("Warning " + warningCount + ": classroom " + classroomID + " already exists.");
        } else {
            Classroom classroom = new Classroom(classroomID, classCapacity);
            classrooms.add(classroom);
        }
        // System.out.println("Classroom1:" + classrooms.get(0).getId());
    }

    @Override
    public void exitAdd_facility(ClassScheduleParser.Add_facilityContext ctx) {
        String classroomID = ctx.classroom_id().CLASSROOM_ID().toString();
        int idx = searchClassroom(classroomID);

        if(idx != -1) {
            ArrayList<ClassScheduleParser.Facility_nameContext> facility_nameContexts =
                    (ArrayList<ClassScheduleParser.Facility_nameContext>) ctx.facilities().facility_name();
            ArrayList<String> facilities = new ArrayList<String>();
            String facilityNameText = "";
            for (ClassScheduleParser.Facility_nameContext facilityName : facility_nameContexts) {
                String words = facilityName.WORD().toString();
                String[] wordList = words.substring(1, words.length() - 1).split(",");
                for (String word : wordList) {
                    facilityNameText = facilityNameText + word;
                }
                facilities.add(facilityNameText);
                facilityNameText = "";
            }

            for(String facility : facilities) {
                classrooms.get(idx).addFacility(facility);
            }
        } else {
            System.out.println("Classroom " + classroomID + " not found");
            System.exit(0);
        }
        // System.out.println("Facilities: " + facilities.toString());
    }

    @Override
    public void exitCreate_lecture(ClassScheduleParser.Create_lectureContext ctx) {

    }

    @Override
    public void exitAdd_requirement(ClassScheduleParser.Add_requirementContext ctx) {

    }

    @Override
    public void exitCreate_lecturer(ClassScheduleParser.Create_lecturerContext ctx) {

    }

    private int searchClassroom(String classroomID) {
        int idx = 0;
        boolean classroomExist = false;
        while(idx < classrooms.size() && !classroomExist) {
            if(classrooms.get(idx).getId().equals(classroomID)) {
                classroomExist = true;
            } else {
                idx++;
            }
        }
        idx = (classroomExist)? idx : -1;
        return idx;
    }
}
