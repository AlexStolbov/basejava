package com.amstolbov.web;

import com.amstolbov.model.ContactType;

public class HtmlUtil {

    public static String ContactTypeToHtml(ContactType ct, String value) {
        if (value == null) {
            return "";
        }
        String prefix = "";
        String title = ct.getTitle();
        switch (ct) {
            case EMAIL:
                prefix = "mailto:";
                title = value;
                break;
            case SKYPE:
                prefix = "skype:";
                title = value;
                break;
        }
        return "<a href='" + prefix + value + "'>" + title + "</a>";
    }

}
