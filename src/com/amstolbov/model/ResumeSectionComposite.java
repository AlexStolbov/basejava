package com.amstolbov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResumeSectionComposite<T> extends ResumeSectionAbstract<T> {

    protected final List<T> parts;

    public ResumeSectionComposite() {
        this.parts = new ArrayList<>();
    }

    @Override
    public void addSectionPart(T sectionPart) {
        parts.add(sectionPart);
    }

    @Override
    public String createRepresentation() {
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
        ResumeSectionComposite that = (ResumeSectionComposite) o;
        return parts.equals(that.parts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parts);
    }
}
