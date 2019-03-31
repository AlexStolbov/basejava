package com.amstolbov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResumeSectionList extends ResumeSectionAbstract<String> {

    protected final List<String> descriptions;

    public ResumeSectionList() {
        this.descriptions = new ArrayList<>();
    }

    @Override
    public void addSectionPart(String sectionPart) {
        descriptions.add(sectionPart);
    }

    @Override
    public String createRepresentation() {
        String res = "";
        for (String part : descriptions) {
            res = res + (res.length() == 0 ? "" : "\n") + " " + part;
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResumeSectionList that = (ResumeSectionList) o;
        return descriptions.equals(that.descriptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptions);
    }
}
