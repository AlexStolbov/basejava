package com.amstolbov.model;

import com.amstolbov.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersonUID = 1L;

    private String name;
    private String url;
    private List<Experience> experiences;

    public Organization() {
    }

    public Organization(String name, String url) {
        this.name = name;
        this.url = url;
        this.experiences = new ArrayList<>();
    }

    public void addExperience(Experience experience) {
        experiences.add(experience);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public List<Experience> getExperiences() {
        return experiences;
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
        return Objects.equals(name, that.name) &&
                Objects.equals(url, that.url) &&
                Objects.equals(experiences, that.experiences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, experiences);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Experience implements Serializable {
        private static final long serialVersonUID = 1L;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateStart;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateFinish;
        private String position;
        private String description;

        public Experience() {
        }

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
