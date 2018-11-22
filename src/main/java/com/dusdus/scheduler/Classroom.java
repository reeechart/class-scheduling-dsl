package com.dusdus.scheduler;

import java.util.ArrayList;

public class Classroom {
    private String id;
    private ArrayList<String> facilities;
    private Integer capacity;

    public Classroom(String id, Integer capacity) {
        facilities = new ArrayList<>();
        this.id  = id;
        this.capacity = capacity;
    }
    public Classroom(String id, Integer capacity, ArrayList<String> facilities) {
        this.id  = id;
        this.capacity = capacity;
        this.facilities = facilities;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getFacilities() {
        return facilities;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addFacility(String facility) {
        this.facilities.add(facility);
    }

    public void delFacilities() {
        this.facilities = new ArrayList<>();
    }

    public void printClassroom() {
        System.out.printf("Id: %s\n", id);
        System.out.printf("Capacity: %d\n",capacity);
        for (String str: facilities) {
            System.out.println(str);
        }
    }
}
