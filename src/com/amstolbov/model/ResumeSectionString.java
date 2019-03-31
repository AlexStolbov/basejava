package com.amstolbov.model;

import java.util.Objects;

public class ResumeSectionString extends ResumeSectionAbstract<String> {

    private String description = "";

    @Override
    public void addSectionPart(String sectionPart) {
        this.description = sectionPart;
    }

    @Override
    public String createRepresentation() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResumeSectionString that = (ResumeSectionString) o;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
