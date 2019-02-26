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

    @Override
    public String toString() {
        return uuid;
    }
}
