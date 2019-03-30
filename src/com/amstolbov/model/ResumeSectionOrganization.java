package com.amstolbov.model;

public class ResumeSectionOrganization extends ResumeSectionAbstract<Organization> {

    @Override
    public String createRepresentation() {
        String res = "";
        for (Organization part : sectionParts) {
            res = res + (res.length() == 0 ? "" : "\n") + " " + part;
        }
        return res;
    }
}
