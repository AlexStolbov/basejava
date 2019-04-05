package com.amstolbov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final String name;
    private final String url;
    private final List<Experience> experiences;

    public Organization(String name, String url) {
        this.name = name;
        this.url = url;
        this.experiences = new ArrayList<>();
    }

    public void addExperience(Experience experience) {
        experiences.add(experience);
    }

    @Override
    public String toString() {
        String res = name + " " + url;
        for (Experience exp : experiences) {
            res = res + "\n" + " " + exp;
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return name.equals(that.name) &&
                experiences.equals(that.experiences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, experiences);
    }

}
