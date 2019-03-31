package com.amstolbov.model;

import java.util.Objects;

public class ResumeSectionSingle<T> extends ResumeSectionAbstract<T> {

    private T description;

    @Override
    public void addSectionInfo(T sectionPart) {
        this.description = sectionPart;
    }

    @Override
    public String createRepresentation() {
        return description.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResumeSectionSingle that = (ResumeSectionSingle) o;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
