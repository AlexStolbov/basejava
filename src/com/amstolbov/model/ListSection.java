package com.amstolbov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection<T> extends SectionAbstract<T> {

    protected final List<T> parts;

    public ListSection() {
        this.parts = new ArrayList<>();
    }

    public void addSectionInfo(T sectionPart) {
        parts.add(sectionPart);
    }

    @Override
    public String toString() {
        String res = "";
        for (T part : parts) {
            res = res + (res.length() == 0 ? "" : "\n") + " " + part;
        }
        return res;
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
