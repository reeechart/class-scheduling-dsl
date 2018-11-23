package com.dusdus.scheduler;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter filename");
        } else {
            TestAntlr(args[0]);
        }
    }

    public static void TestAntlr(String filename) {
        try {
            InputStream is = new FileInputStream(filename);
            CharStream cs = new ANTLRInputStream(is);
            ClassScheduleLexer classScheduleLexer = new ClassScheduleLexer(cs);
            CommonTokenStream tokens = new CommonTokenStream(classScheduleLexer);
            ClassScheduleParser classScheduleParser = new ClassScheduleParser(tokens);

            Timetable timetable = new Timetable();
            ArrayList<Lecture> lectures = new ArrayList<Lecture>();
            ArrayList<Classroom> classrooms = new ArrayList<Classroom>();
            ConflictingConstraint constraints = new ConflictingConstraint();
            ClassroomPreferences classroomPreferences = new ClassroomPreferences();
            classScheduleParser.addParseListener(new ClassScheduleParseTreeListener(lectures, classrooms, constraints, classroomPreferences));
            classScheduleParser.program();

            Scheduler scheduler = new Scheduler(timetable, lectures, classrooms);
            scheduler.addConflictingConstraint(constraints);
            scheduler.addClassroomPreferences(classroomPreferences);
            scheduler.schedule();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
