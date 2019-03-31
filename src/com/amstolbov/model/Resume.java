package com.amstolbov.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<ContactType, Contact> contacts;
    private final Map<ResumeSectionType, ResumeSectionAbstract> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "full name must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = new EnumMap<>(ContactType.class);
        this.sections = new EnumMap<>(ResumeSectionType.class);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void addContact(ContactType type, Contact contact) {
        this.contacts.put(type, contact);
    }

    public void addSection(ResumeSectionType type, ResumeSectionAbstract section) {
        sections.put(type, section);
    }

    public Map<ContactType, Contact> getContacts() {
        return contacts;
    }

    public Map<ResumeSectionType, ResumeSectionAbstract> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName) &&
                contacts.equals(resume.getContacts()) &&
                sections.equals(resume.getSections());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int comp = fullName.compareTo(o.getFullName());
        return comp != 0 ? comp : uuid.compareTo(o.getUuid());
    }

}
