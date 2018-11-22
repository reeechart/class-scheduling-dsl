package com.dusdus.scheduler;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class ClassScheduleParseTreeListener extends ClassScheduleBaseListener {
    private ArrayList<Lecture> lectures;
    private ArrayList<Classroom> classrooms;
    private ArrayList<Lecturer> lecturers;
    private ConflictingConstraint constraints;
    private int warningCount = 0;

    private final int MIN_DAY = 1;
    private final int MAX_DAY = 7;
    private final int MIN_TIME = 0;
    private final int MAX_TIME = 23;

    public ClassScheduleParseTreeListener(ArrayList<Lecture> lectures, ArrayList<Classroom> classrooms, ConflictingConstraint constraints) {
        this.lectures = lectures;
        this.classrooms = classrooms;
        this.constraints = constraints;
        lecturers = new ArrayList<Lecturer>();
    }

//    public void enterProgram(ClassScheduleParser.ProgramContext ctx) {
//        System.out.println("Enter Program");
//    }
//
//    @Override
//    public void enterCommand(ClassScheduleParser.CommandContext ctx) {
//        System.out.println("Enter Command");
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
            exitProgramError();
        }
        // System.out.println("Facilities: " + facilities.toString());
    }

    @Override
    public void exitCreate_lecture(ClassScheduleParser.Create_lectureContext ctx) {
        String lectureID = ctx.LECTURE_ID().toString();
        String lecturerName = extractWORDS(ctx.lecture_params().lecturer_name().WORD());
        int maxParticipant = Integer.parseInt(ctx.lecture_params().max_participant().NUM().toString());
        int credits = Integer.parseInt(ctx.lecture_params().credits().NUM().toString());

        String errorMessages = "";
        if(credits > 10) {
            errorMessages = "Max credits limit exceeded. Max: 10, Found: " + credits + "\n";
        }
        int lecturer_idx = searchLecturer(lecturerName);
        if(lecturer_idx == -1) {
            errorMessages = errorMessages + "Lecturer " + lecturerName + " not found.";
        }
        if(!errorMessages.equals("")) {
            printError(errorMessages, ctx.getText());
            exitProgramError();
        }

        Lecture lecture = new Lecture(lectureID, maxParticipant, credits);
        lecture.setLecturer(lecturers.get(lecturer_idx));
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
            exitProgramError();
        }
    }

    @Override
    public void exitCreate_lecturer(ClassScheduleParser.Create_lecturerContext ctx) {
        String lecturerName = extractWORDS(ctx.lecturer_name().WORD());
        Lecturer lecturer = new Lecturer(lecturerName);
        int lecturerIndex = searchLecturer(lecturerName);
        if (lecturerIndex == -1) {
            lecturers.add(lecturer);
        } else {
            printWarning("Lecturer " + lecturerName + " already exists", ctx.getText());
        }
    }

    @Override
    public void exitAdd_lecturer_availability(ClassScheduleParser.Add_lecturer_availabilityContext ctx) {
        String lecturerName = extractWORDS(ctx.lecturer_name().WORD());
        int lecturerIndex = searchLecturer(lecturerName);
        if (lecturerIndex != -1) {
            Lecturer lecturer = lecturers.get(lecturerIndex);
            addScheduleToLecturer(lecturer, ctx.schedule().time_slot());
        } else {
            printError("Lecturer " + lecturerName + " not found.", ctx.getText());
            exitProgramError();
        }
    }

    @Override
    public void exitAdd_constraint(ClassScheduleParser.Add_constraintContext ctx) {
        String[] lectureIDs = new String[2];
        lectureIDs[0] = ctx.LECTURE_ID(0).toString();
        lectureIDs[1] = ctx.LECTURE_ID(1).toString();

        String errorMessage = "";
        for(String lectureID: lectureIDs) {
            if(searchLecture(lectureID) == -1) {
                errorMessage = errorMessage + "Lecture " + lectureID + " not found. \n";
            }
        }
        if(!errorMessage.equals("")) {
            printError(errorMessage, ctx.getText());
            exitProgramError();
        }

        constraints.addKeyValue(lectureIDs[0], lectureIDs[1]);
        constraints.addKeyValue(lectureIDs[1], lectureIDs[0]);
    }

    @Override
    public void exitAdd_preference(ClassScheduleParser.Add_preferenceContext ctx) {
        System.out.println("Lecture " + ctx.LECTURE_ID() + " preferably placed " + ctx.COMPARATOR() + " " + ctx.hour_of_day());
    }

//    @Override
//    public void exitCommand(ClassScheduleParser.CommandContext ctx) {
//        System.out.println("Exit Command");
//    }
//
//    @Override
//    public void exitProgram(ClassScheduleParser.ProgramContext ctx) {
//        System.out.println("Exit Program");
//        super.exitProgram(ctx);
//    }

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

    private void addScheduleToLecturer(Lecturer lecturer, List<ClassScheduleParser.Time_slotContext> timeSlots) {
        for (ClassScheduleParser.Time_slotContext timeSlot : timeSlots) {
            int day = Integer.parseInt(timeSlot.day_number().NUM().toString());
            int time = Integer.parseInt(timeSlot.hour_of_day().NUM().toString());
            if (isDayValid(day) && (isTimeValid(time))) {
                Schedule schedule = new Schedule(day, time);
                lecturer.addSchedule(schedule);
            } else {
                String cause = timeSlot.day_number().NUM().toString() + " " + timeSlot.hour_of_day().NUM().toString();
                printError("Invalid day and time", cause);
                System.exit(1);
            }
        }
    }

    private boolean isDayValid(int day) {
        return ((day >= MIN_DAY) && (day <= MAX_DAY));
    }

    private boolean isTimeValid(int time) {
        return ((time >= MIN_TIME) && (time <= MAX_TIME));
    }

    private int searchLecturer(String lecturerName) {
        int idx = 0;
        boolean lecturerExist = false;
        while (idx < lecturers.size() && !lecturerExist) {
            if (lecturers.get(idx).getName().equals(lecturerName)) {
                lecturerExist = true;
            } else {
                idx++;
            }
        }
        return lecturerExist ? idx : -1;
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

    private void exitProgramError() {
        if(warningCount > 0) {
            System.out.println("Warning total: " + warningCount + " warnings.");
        }
        System.out.println("Process terminated");
        System.exit(0);
    }
}
