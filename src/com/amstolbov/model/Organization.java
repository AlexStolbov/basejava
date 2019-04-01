package com.amstolbov.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private String name;
    private String url;
    private List<Experience> experiences;

    public Organization(String name, String url) {
        this.name = name;
        this.url = url;
        this.experiences = new ArrayList<>();
    }

    public void addExperience(LocalDate start, LocalDate finish, String position, String descr) {
        experiences.add(new Experience(start, finish, position, descr));
    }

    @Override
    public String toString() {
        String res = name;
        for (Experience exp : experiences) {
            res = res + "\n" + " " + exp;
        }
        return res;
    }

    private class Experience {
        private final LocalDate dateStart;
        private final LocalDate dateFinish;
        private final String position;
        private final String description;

        public Experience(LocalDate dateStart, LocalDate dateFinish, String position, String descr) {
            this.dateStart = dateStart;
            this.dateFinish = dateFinish;
            this.position = position;
            this.description = descr;
        }

        @Override
        public String toString() {
            String posToPrint = position.length() == 0 ? "" : " " + position;
            return String.format("с %s по %s %s \n %s", dateStart, dateFinish, posToPrint, description);
        }

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
