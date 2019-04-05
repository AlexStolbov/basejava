package com.amstolbov.model;

import java.time.LocalDate;
import java.util.Objects;

public class Experience {
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
        return String.format("с %s по %s %s \n %s \n", dateStart, dateFinish, posToPrint, description);

    }
}
