package com.amstolbov.model;

/**
 * Resume section.
 * @param <T> - type of information in section
 */
public abstract class ResumeSectionAbstract<T> {

    /**
     * Add new information in section.
     */
    public abstract void addSectionPart(T sectionPart);

    /**
     * Create representation of section.
     * @return - view of section
     */
    public abstract String createRepresentation();

    @Override
    public String toString() {
        return createRepresentation();
    }

}
