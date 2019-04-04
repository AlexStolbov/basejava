package com.amstolbov;

import com.amstolbov.model.*;
import com.amstolbov.util.DateUtil;

import java.time.Month;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        ResumeTestData.printTest(ResumeTestData.fillResume(new Resume(addRandom("Resume name "))));
    }

    public static Resume fillResume(Resume resume) {
        ResumeTestData.addContacts(resume);
        ResumeTestData.addObjective(resume);
        ResumeTestData.addPersonal(resume);
        ResumeTestData.addAchievement(resume);
        ResumeTestData.addQualification(resume);
        ResumeTestData.addExperience(resume);
        ResumeTestData.addEducation(resume);
        return resume;
    }

    private static void addContacts(Resume resume) {
        resume.addContact(ContactType.PHONE, addRandom("phone number"));
        resume.addContact(ContactType.SKYPE, addRandom("skype number"));
    }

    private static void addObjective(Resume resume) {
        SimpleTextSection sectionObjective = new SimpleTextSection(addRandom("objective text"));
        resume.addSection(ResumeSectionType.OBJECTIVE, sectionObjective);
    }

    private static void addPersonal(Resume resume) {
        SimpleTextSection sectionPersonal = new SimpleTextSection(addRandom("personal text"));
        resume.addSection(ResumeSectionType.PERSONAL, sectionPersonal);
    }

    private static void addAchievement(Resume resume) {
        ListSection sectionAchievement = new ListSection();
        sectionAchievement.addSectionPart(addRandom("achievement 1 -"));
        sectionAchievement.addSectionPart(addRandom("achievement 2 -"));
        sectionAchievement.addSectionPart(addRandom("achievement 3 -"));
        resume.addSection(ResumeSectionType.ACHIEVEMENT, sectionAchievement);
    }

    private static void addQualification(Resume resume) {
        ListSection sectionQualifications = new ListSection();
        sectionQualifications.addSectionPart(addRandom("qualification 1 -"));
        sectionQualifications.addSectionPart(addRandom("qualification 2 -"));
        sectionQualifications.addSectionPart(addRandom("qualification 3 -"));
        resume.addSection(ResumeSectionType.QUALIFICATIONS, sectionQualifications);
    }

    private static void  addExperience(Resume resume) {
        OrganizationSection jobs = new OrganizationSection();
        for (int i = 0; i < 2; i++) {
            Organization org = new Organization(addRandom("Software company"), addRandom("url"));
            for (int y = 0; y < 2; y++) {
                int border = randomFromRange(1998, 2019);
                org.addExperience(DateUtil.of(randomFromRange(1998, border), Month.values()[randomFromRange(0, 11)])
                        , DateUtil.of(randomFromRange(border, 2019), Month.values()[randomFromRange(0, 11)])
                        , addRandom("position")
                        , addRandom("position description"));
            }
            jobs.addOrganization(org);
        }
        resume.addSection(ResumeSectionType.EXPERIENCE, jobs);
    }

    private static void addEducation(Resume resume) {
        OrganizationSection educations = new OrganizationSection();
        for (int i = 0; i < 2; i++) {
            Organization org = new Organization(addRandom("School"), addRandom("url"));
            for (int y = 0; y < 2; y++) {
                int border = randomFromRange(2000, 2019);
                org.addExperience(DateUtil.of(randomFromRange(1998, border), Month.values()[randomFromRange(0, 11)])
                        , DateUtil.of(randomFromRange(border, 2019), Month.values()[randomFromRange(0, 11)])
                        , ""
                        , addRandom("training course"));
            }
            educations.addOrganization(org);
        }
        resume.addSection(ResumeSectionType.EDUCATION, educations);
    }

    private static void printTest(Resume resume) {
        for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
            System.out.println(pair.getKey() + "\n" + pair.getValue());
        }
        for (Map.Entry<ResumeSectionType, AbstractSection> pair : resume.getSections().entrySet()) {
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
