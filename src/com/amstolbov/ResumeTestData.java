package com.amstolbov;

import com.amstolbov.model.*;
import com.amstolbov.util.DateUtil;

import java.time.Month;
import java.util.Map;
import java.util.stream.Stream;

public class ResumeTestData {

    public static final SectionType[] ALL_SECTIONS = SectionType.values();
    public static final SectionType[] WITHOUT_EXPERIENCE = Stream.of(ALL_SECTIONS)
            .filter(el -> el != SectionType.EXPERIENCE)
            .toArray(SectionType[]::new);
    public static final SectionType[] WITHOUT_CONTACTS_SECTIONS = new SectionType[0];
    public static final SectionType[] ONLY_CONTACTS = new SectionType[0];

    public static void main(String[] args) {
        ResumeTestData.printTest(ResumeTestData.getResume(addRandom(""), addRandom("Resume name "), ALL_SECTIONS));
    }

    public static Resume getResume(String uuid, String fullName, SectionType[] includedSection) {
        Resume resume = new Resume(uuid, fullName);

        if (includedSection != WITHOUT_CONTACTS_SECTIONS) {
            ResumeTestData.addContacts(resume);
        }

        for (SectionType include : includedSection) {
            switch (include) {
                case OBJECTIVE:
                    ResumeTestData.addObjective(resume);
                    break;
                case PERSONAL:
                    ResumeTestData.addPersonal(resume);
                    break;
                case ACHIEVEMENT:
                    ResumeTestData.addAchievement(resume);
                    break;
                case QUALIFICATIONS:
                    ResumeTestData.addQualification(resume);
                    break;
                case EXPERIENCE:
                    ResumeTestData.addExperience(resume);
                    break;
                case EDUCATION:
                    ResumeTestData.addEducation(resume);
            }
        }
        return resume;
    }

    private static void addContacts(Resume resume) {
        resume.addContact(ContactType.PHONE, addRandom("phone number"));
        resume.addContact(ContactType.SKYPE, addRandom("skype number"));
    }

    private static void addObjective(Resume resume) {
        SimpleTextSection sectionObjective = new SimpleTextSection(addRandom("objective text"));
        resume.addSection(SectionType.OBJECTIVE, sectionObjective);
    }

    private static void addPersonal(Resume resume) {
        SimpleTextSection sectionPersonal = new SimpleTextSection(addRandom("personal text"));
        resume.addSection(SectionType.PERSONAL, sectionPersonal);
    }

    private static void addAchievement(Resume resume) {
        ListSection sectionAchievement = new ListSection();
        sectionAchievement.addSectionPart(addRandom("achievement 1 -"));
        sectionAchievement.addSectionPart(addRandom("achievement 2 -"));
        sectionAchievement.addSectionPart(addRandom("achievement 3 -"));
        resume.addSection(SectionType.ACHIEVEMENT, sectionAchievement);
    }

    private static void addQualification(Resume resume) {
        ListSection sectionQualifications = new ListSection();
        sectionQualifications.addSectionPart(addRandom("qualification 1 -"));
        sectionQualifications.addSectionPart(addRandom("qualification 2 -"));
        sectionQualifications.addSectionPart(addRandom("qualification 3 -"));
        resume.addSection(SectionType.QUALIFICATIONS, sectionQualifications);
    }

    private static void addExperience(Resume resume) {
        OrganizationSection jobs = new OrganizationSection();
        for (int i = 0; i < 2; i++) {
            Organization org = new Organization(addRandom("Software company"), i == 1 ? null : addRandom("url"));
            for (int y = 0; y < 2; y++) {
                int border = randomFromRange(1998, 2019);
                Organization.Experience experience = new Organization.Experience(
                        DateUtil.of(randomFromRange(1998, border), Month.values()[randomFromRange(0, 11)])
                        , DateUtil.of(randomFromRange(border, 2019), Month.values()[randomFromRange(0, 11)])
                        , addRandom(addRandom("position"))
                        , (y == 1 ? null : addRandom("position description")));
                org.addExperience(experience);
            }
            jobs.addOrganization(org);
        }
        resume.addSection(SectionType.EXPERIENCE, jobs);
    }

    private static void addEducation(Resume resume) {
        OrganizationSection educations = new OrganizationSection();
        for (int i = 0; i < 2; i++) {
            Organization org = new Organization(addRandom("School"), addRandom("url"));
            for (int y = 0; y < 2; y++) {
                int border = randomFromRange(2000, 2019);
                Organization.Experience experience = new Organization.Experience(
                        DateUtil.of(randomFromRange(1998, border), Month.values()[randomFromRange(0, 11)])
                        , DateUtil.of(randomFromRange(border, 2019), Month.values()[randomFromRange(0, 11)])
                        , ""
                        , addRandom("training course")
                );
                org.addExperience(experience);
            }
            educations.addOrganization(org);
        }
        resume.addSection(SectionType.EDUCATION, educations);
    }

    private static void printTest(Resume resume) {
        for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
            System.out.println(pair.getKey() + "\n" + pair.getValue());
        }
        for (Map.Entry<SectionType, AbstractSection> pair : resume.getSections().entrySet()) {
            System.out.println(pair.getKey() + "\n" + pair.getValue());
        }
    }

    private static String addRandom(String name) {
        //return name + String.format(" %d", new Random(System.currentTimeMillis()).nextInt());
        return name + " " + randomFromRange(0, 10000000);
    }

    private static int randomFromRange(int min, int max) {
        return min + (int) Math.round(Math.random() * (max - min));
    }

}
