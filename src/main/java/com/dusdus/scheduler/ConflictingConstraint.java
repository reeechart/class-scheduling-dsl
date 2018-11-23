package com.dusdus.scheduler;

import java.util.ArrayList;
import java.util.HashMap;

public class ConflictingConstraint {
    private HashMap<String, ArrayList<String>> constraintList;

    public ConflictingConstraint() {
        this.constraintList = new HashMap<>();
    }

    public ArrayList<String> getValueList(String key) {
        return constraintList.getOrDefault(key, null);
    }

    public void addKeyValue(String key, String value) {
        ArrayList<String> valueList;

        if (this.constraintList.containsKey(key)) {
            valueList = this.constraintList.get(key);
        } else {
            valueList = new ArrayList<>();
        }

        if (!valueList.contains(value)) {
            valueList.add(value);
        }
        this.constraintList.put(key, valueList);
    }

    public void delKeyValue(String key) {
        this.constraintList.remove(key);
    }
}
