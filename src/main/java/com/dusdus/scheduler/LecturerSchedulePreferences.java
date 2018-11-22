package com.dusdus.scheduler;

import java.util.*;

// Time periods are inclusive (bounds are included)

public class LecturerSchedulePreferences {

    public final static int BEFORE = -1;
    public final static int BETWEEN = 0;
    public final static int AFTER = 1;

    public final static String BEFORE_STRING = "BEFORE";
    public final static String BETWEEN_STRING = "BETWEEN";
    public final static String AFTER_STRING = "AFTER";

    private ArrayList<Preference> preferences;

    public LecturerSchedulePreferences() {
        preferences = new ArrayList<>();
    }

    public void addPreference(int relationship, Schedule schedule, float weight) {
        assert relationship != BETWEEN;
        preferences.add(new Preference(relationship, schedule, weight));
    }

    public void addPreferenceBetween(Schedule lowerBound, Schedule upperBound, float weight) {
        preferences.add(new Preference(BETWEEN, lowerBound, upperBound, weight));
    }

    public ArrayList<Schedule> applyPreferences(ArrayList<Schedule> availability) {
        ArrayList<WeightedSchedule> weightedSchedules = new ArrayList<>(availability.size());

        // Construct weighted schedule list from preferences list
        for (Schedule s: availability) {
            float weight = 0;
            for (Preference p: preferences) {
                if (!s.getDay().equals(p.lowerBound.getDay())) {
                    continue;
                }

                switch (p.relationship) {
                    case BEFORE:
                        if (s.getTime() <= p.lowerBound.getTime()) {
                            weight = p.weight;
                        }
                        break;
                    case BETWEEN:
                        if (s.getTime() >= p.lowerBound.getTime() && s.getTime() <= p.upperBound.getTime()) {
                            weight = p.weight;
                        }
                        break;
                    case AFTER:
                        if (s.getTime() >= p.lowerBound.getTime()) {
                            weight = p.weight;
                        }
                }
            }

            weightedSchedules.add(new WeightedSchedule(s, weight));
        }

        // Sort to decreasing weight
        weightedSchedules.sort((s1, s2) -> -1 * Float.compare(s1.weight, s2.weight));

        // Retrieve sorted schedule
        ArrayList<Schedule> sortedSchedule = new ArrayList<>(availability.size());
        for (WeightedSchedule w: weightedSchedules) {
            sortedSchedule.add(w.schedule);
        }
        return sortedSchedule;
    }

    public class Preference {
        private int relationship;
        private Schedule lowerBound;
        private Schedule upperBound;
        private float weight;

        public Preference(int relationship, Schedule schedule, float weight) {
            this.relationship = relationship;
            this.lowerBound = schedule;
            this.weight = weight;
        }

        public Preference(int relationship, Schedule lowerBound, Schedule upperBound, float weight) {
            this.relationship = relationship;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.weight = weight;
        }
    }

    private class WeightedSchedule {
        public Schedule schedule;
        public float weight;

        public WeightedSchedule(Schedule schedule, float weight) {
            this.schedule = schedule;
            this.weight = weight;
        }
    }
}
