package com.amstolbov.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersonUID = 1L;
    private final List<String> parts = new ArrayList<>();

    public ListSection() {

    }

    public ListSection(String... parts) {
        this.parts.addAll(Arrays.asList(parts));
    }

    public void addSectionPart(String sectionPart) {
        parts.add(sectionPart);
    }

    public List<String> getParts() {
        return parts;
    }

    @Override
    public String toString() {
        return String.join("\n", parts);
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
