package com.dusdus.scheduler;

public class Schedule {

    private String start;
    private Integer hour;
    private Integer day;

    public Schedule(String start) {
        this.start = start;
        String[] schedule = start.split(",");
        hour = Integer.parseInt(schedule[1]);
        day = Integer.parseInt(schedule[0]);
    }

    public Integer getHour() {
        return hour;
    }

    public Integer getDay() {
        return day;
    }

    public String getStartTimeString() {
        return start;
    }

    public void printSchedule() {
        System.out.printf("%d %d\n", day, hour);
    }

    public boolean compareSchedule(Schedule schedule) {
        return schedule.getDay().equals(day) && schedule.getHour().equals(hour);
    }
}
