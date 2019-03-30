package com.amstolbov.model;

public class ResumeSectionString extends ResumeSectionAbstract<String> {

    public String createRepresentation() {
        String res = "";
        for (String part : sectionParts) {
            res = res + (res.length() == 0 ? "" : "\n") + " - " + part;
        }
        return res;
    }
}
