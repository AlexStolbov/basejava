package com.amstolbov.model;

import java.util.Objects;

public class SimpleTextSection<T> extends SectionAbstract<T> {

    private T description;

    public void addSectionInfo(T sectionPart) {
        this.description = sectionPart;
    }

    @Override
    public String toString() {
        return description.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleTextSection that = (SimpleTextSection) o;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
