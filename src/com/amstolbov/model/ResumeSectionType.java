package com.amstolbov.model;

public enum ResumeSectionType {
    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    ResumeSectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
