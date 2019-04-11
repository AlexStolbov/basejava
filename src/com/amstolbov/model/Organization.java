package com.amstolbov.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersonUID = 1L;

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

    public static class Experience implements Serializable {
        private static final long serialVersonUID = 1L;

        private final LocalDate dateStart;
        private final LocalDate dateFinish;
        private final String position;
        private final String description;

        public Experience(LocalDate dateStart, LocalDate dateFinish, String position, String descr) {
            Objects.requireNonNull(dateStart, "Experience dateStart must not be null");
            Objects.requireNonNull(dateFinish, "Experience dateFinish must not be null");
            Objects.requireNonNull(position, "Experience position must not be null");
            this.dateStart = dateStart;
            this.dateFinish = dateFinish;
            this.position = position;
            this.description = descr;
        }

        public LocalDate getDateStart() {
            return dateStart;
        }

        public LocalDate getDateFinish() {
            return dateFinish;
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Experience that = (Experience) o;
            return dateStart.equals(that.dateStart) &&
                    dateFinish.equals(that.dateFinish) &&
                    position.equals(that.position) &&
                    Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dateStart, dateFinish, position, description);
        }

        @Override
        public String toString() {
            String posToPrint = position.length() == 0 ? "" : " " + position;
            return String.format("с %s по %s %s %n %s %n", dateStart, dateFinish, posToPrint, description);

        }
    }

}
