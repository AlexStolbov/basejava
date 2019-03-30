package com.amstolbov.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Organization {
    private String name;
    private List<Experience> experience;

    public Organization(String name) {
        this.name = name;
        this.experience = new ArrayList<>();
    }

    public void addExperience(LocalDate start, LocalDate finish, String position, String descr) {
        experience.add(new Experience(start, finish, position, descr));
    }

    @Override
    public String toString() {
        String res = name;
        for (Experience exp : experience) {
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

}
