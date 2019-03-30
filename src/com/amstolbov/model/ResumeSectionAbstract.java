package com.amstolbov.model;

import java.util.ArrayList;
import java.util.List;

public abstract class ResumeSectionAbstract<T> {
    protected final List<T> sectionParts;

    public ResumeSectionAbstract() {
        this.sectionParts = new ArrayList<>();
    }

    public void addSectionPart(T sectionPart) {
        sectionParts.add(sectionPart);
    }

    @Override
    public String toString() {
        return createRepresentation();
    }

    public abstract String createRepresentation();

}
