package com.dusdus.scheduler;

public class ClassScheduleParseTreeListener extends ClassScheduleBaseListener {
    private Timetable timetable;

    public ClassScheduleParseTreeListener(Timetable timetable) {
        this.timetable = timetable;
    }

    @Override
    public void enterCreate_classroom(ClassScheduleParser.Create_classroomContext ctx) {
        System.out.println("Classroom ID:" + ctx.classroom_id().CLASSROOM_ID());
        System.out.println("Classroom Capacity " + ctx.capacity().NUM());
    }
}
