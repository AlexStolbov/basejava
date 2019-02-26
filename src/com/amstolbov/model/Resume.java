package com.amstolbov.model;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;
    private String name;

    public Resume(String newUUID) {
        uuid = newUUID;
    }

    public String getUuid() {
        return uuid;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
