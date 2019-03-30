package com.amstolbov.model;

public enum ContactType {
    PHONE("Phone."),
    SKYPE("Skype"),
    EMAIL("email"),
    LINKEDIN(""),
    GITHUB(""),
    STACKOVERFLOW(""),
    HOME("");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
