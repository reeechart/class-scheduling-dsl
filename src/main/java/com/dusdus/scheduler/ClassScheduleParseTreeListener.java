package com.dusdus.scheduler;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

//    public void enterProgram(ClassScheduleParser.ProgramContext ctx) {
//        System.out.println("Enter Program");
//    }

    @Override
    public void exitCreate_classroom(ClassScheduleParser.Create_classroomContext ctx) {
        // System.out.println("Classroom ID:" + ctx.classroom_id().CLASSROOM_ID());
        // System.out.println("Classroom Capacity " + ctx.capacity().NUM());
        String classroomID = ctx.classroom_id().CLASSROOM_ID().toString();
        int classCapacity = Integer.parseInt(ctx.capacity().NUM().toString());
        int idx = searchClassroom(classroomID);
        if(idx != -1) {
            printWarning("Classroom " + classroomID + " already exists.", ctx.getText());
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
                facilityNameText = extractWORDS(facilityName.WORD());
                facilities.add(facilityNameText);
            }

            for(String facility : facilities) {
                classrooms.get(idx).addFacility(facility);
            }
        } else {
            printError("Classroom " + classroomID + " not found", ctx.getText());
            System.exit(0);
        }
        // System.out.println("Facilities: " + facilities.toString());
    }

    @Override
    public void exitCreate_lecture(ClassScheduleParser.Create_lectureContext ctx) {
        String lectureID = ctx.LECTURE_ID().toString();
        String lecturerName = extractWORDS(ctx.lecture_params().lecturer_name().WORD());
        int maxParticipant = Integer.parseInt(ctx.lecture_params().max_participant().NUM().toString());
        int credits = Integer.parseInt(ctx.lecture_params().credits().NUM().toString());
        if(credits > 10) {
            printError("Max credits limit exceeded. Max: 10, Found: " + credits, ctx.getText());
        }
        Lecture lecture = new Lecture(lectureID, maxParticipant, credits);
        lectures.add(lecture);
    }

    @Override
    public void exitAdd_requirement(ClassScheduleParser.Add_requirementContext ctx) {
        String lectureID = ctx.LECTURE_ID().toString();
        int idx = searchLecture(lectureID);
        if(idx != -1) {
            ArrayList<ClassScheduleParser.Facility_nameContext> facility_nameContexts =
                    (ArrayList<ClassScheduleParser.Facility_nameContext>) ctx.facilities().facility_name();
            ArrayList<String> facilities = new ArrayList<String>();
            String facilityNameText = "";
            for (ClassScheduleParser.Facility_nameContext facilityName : facility_nameContexts) {
                facilityNameText = extractWORDS(facilityName.WORD());
                facilities.add(facilityNameText);
            }

            for(String facility : facilities) {
                lectures.get(idx).addFacility(facility);
            }
        } else {
            printError("Lecture " + lectureID + " not found", ctx.getText());
            System.exit(0);
        }
    }

    @Override
    public void exitCreate_lecturer(ClassScheduleParser.Create_lecturerContext ctx) {

    }

    private String extractWORDS(List<TerminalNode> WORDS) {
        String extracted = "";
        int wordCount = 0;
        for(TerminalNode word : WORDS) {
            if(wordCount == 0) {
                extracted = word.toString();
            } else {
                extracted = extracted + " " + word.toString();
            }
            wordCount++;
        }

        return extracted;
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

    private int searchLecture(String lectureID) {
        int idx = 0;
        boolean lectureExist = false;
        while(idx < lectures.size() && !lectureExist) {
            if(lectures.get(idx).getId().equals(lectureID)) {
                lectureExist = true;
            } else {
                idx++;
            }
        }
        idx = (lectureExist)? idx : -1;
        return idx;
    }

    private void printError(String message, String cause) {
        System.out.println("Error: " + message);
        System.out.println("Cause: " + cause);
        System.out.println();
    }

    private void printWarning(String message, String cause) {
        warningCount++;
        System.out.println("Warning " + warningCount + ": " + message);
        System.out.println("Cause: " + cause);
        System.out.println();
    }
}
