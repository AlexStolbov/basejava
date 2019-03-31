package com.amstolbov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResumeSectionOrganization extends ResumeSectionAbstract<Organization> {

    protected final List<Organization> organizations;

    public ResumeSectionOrganization() {
        this.organizations = new ArrayList<>();
    }

    @Override
    public void addSectionPart(Organization sectionPart) {
        organizations.add(sectionPart);
    }

    @Override
    public String createRepresentation() {
        String res = "";
        for (Organization part : organizations) {
            res = res + (res.length() == 0 ? "" : "\n") + " " + part;
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResumeSectionOrganization that = (ResumeSectionOrganization) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }
}
