package com.amstolbov.model;

import java.util.Objects;

public class SimpleTextSection extends AbstractSection {
    private static final long serialVersonUID = 1L;

    private final String description;

    public SimpleTextSection(String descr) {
        this.description = descr;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleTextSection that = (SimpleTextSection) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
