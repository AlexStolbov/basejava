package com.amstolbov.model;

import java.util.Objects;

public class Contact {
    private final String URL;

    public Contact(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    @Override
    public String toString() {
        return URL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return URL.equals(contact.URL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(URL);
    }
}

