package com.amstolbov.model;

public enum ContactType {
    PHONE("phone."),
    SKYPE("skype"),
    EMAIL("email"),
    LINKEDIN("linkedin"),
    GITHUB("github"),
    STACKOVERFLOW("stackoverflow"),
    HOME("home");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
