package com.dusdus.scheduler;

public class Schedule {

    private String start;
    private Integer time;
    private Integer day;

    public Schedule(int day, int time) {
        this.start = start;
        this.day = day;
        this.time = time;
    }

    public Integer getTime() {
        return time;
    }

    public Integer getDay() {
        return day;
    }

    public String getStartTimeString() {
        return start;
    }

    public void printSchedule() {
        System.out.printf("%d %d\n", day, time);
    }

    public boolean compareSchedule(Schedule schedule) {
        return schedule.getDay().equals(day) && schedule.getTime().equals(time);
    }
}
