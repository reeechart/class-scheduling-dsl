package com.dusdus.scheduler;

import java.util.HashMap;

public class ConflictingConstraint {
    private HashMap<String, String> constraintList;

    public ConflictingConstraint() {
        this.constraintList = new HashMap<>();
    }

    public String getValue(String key) {
        if (constraintList.containsKey(key)) {
            return constraintList.get(key);
        } else {
            return "";
        }

    }

    public void addKeyValue(String key, String value) {
        this.constraintList.put(key, value);
    }

    public void delKeyValue(String key) {
        this.constraintList.remove(key);
    }
}
