package com.amstolbov.model;

import java.awt.*;

public class Contact {
    private final Image picture;
    private final String URL;

    public Contact(Image picture, String URL) {
        this.picture = picture;
        this.URL = URL;
    }

    public Image getPicture() {
        return picture;
    }

    public String getURL() {
        return URL;
    }

    @Override
    public String toString() {
        return URL;
    }
}

