package com.amstolbov.model;

public abstract class ResumeSectionAbstract<T> {

    public abstract void addSectionPart(T sectionPart);

    public abstract String createRepresentation();

    @Override
    public String toString() {
        return createRepresentation();
    }

}
