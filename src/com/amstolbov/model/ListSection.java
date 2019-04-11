package com.amstolbov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersonUID = 1L;

    private final List<String> parts = new ArrayList<>();

    public void addSectionPart(String sectionPart) {
        parts.add(sectionPart);
    }

    @Override
    public String toString() {
        return parts.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return parts.equals(that.parts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parts);
    }
}
