package com.dusdus.scheduler;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassroomPreferences {

    public HashMap<String, ArrayList<WeightedClassroom>> preferences;

    public ClassroomPreferences() {
        preferences = new HashMap<>();
    }

    public void addPreference(String lectureID, Classroom classroom, float weight) {
        preferences.computeIfAbsent(lectureID, k -> new ArrayList<>());
        preferences.get(lectureID).add(new WeightedClassroom(classroom, weight));
    }

    public ArrayList<Classroom> applyPreferences(ArrayList<Classroom> classrooms, Lecture lecture) {
        if (!preferences.containsKey(lecture.getId())) {
            return classrooms;
        }
        ArrayList<WeightedClassroom> preferredClassrooms = preferences.get(lecture.getId());

        ArrayList<WeightedClassroom> weightedClassrooms = new ArrayList<>(classrooms.size());
        for (Classroom c: classrooms) {
            boolean inserted = false;
            for (WeightedClassroom pc: preferredClassrooms) {
                if (c.getId().equals(pc.classroom.getId())) {
                    weightedClassrooms.add(pc);
                    inserted = true;
                    break;
                }
            }

            if (!inserted) {
                weightedClassrooms.add(new WeightedClassroom(c, 0));
            }
        }

        weightedClassrooms.sort((c1, c2) -> -1 * Float.compare(c1.weight, c2.weight));

        ArrayList<Classroom> sortedClassroom = new ArrayList<>(classrooms.size());
        for (WeightedClassroom wc: weightedClassrooms) {
            sortedClassroom.add(wc.classroom);
        }
        return sortedClassroom;
    }

    public class WeightedClassroom {
        public Classroom classroom;
        public float weight;

        public WeightedClassroom(Classroom classroom, float weight) {
            this.classroom = classroom;
            this.weight = weight;
        }
    }
}
