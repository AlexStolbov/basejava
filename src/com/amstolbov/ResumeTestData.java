package com.amstolbov;

import com.amstolbov.model.*;

import java.time.LocalDate;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Grigory Kislin");

        ResumeTestData.addContacts(resume);
        ResumeTestData.addObjective(resume);
        ResumeTestData.addPersonal(resume);
        ResumeTestData.addAchievement(resume);
        ResumeTestData.addQualification(resume);
        ResumeTestData.addExperience(resume);
        ResumeTestData.addEducation(resume);

        ResumeTestData.printTest(resume);
    }

    private static void addContacts(Resume resume) {
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
    }

    private static void addObjective(Resume resume) {
        SimpleTextSection sectionObjective = new SimpleTextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addSection(ResumeSectionType.OBJECTIVE, sectionObjective);
    }

    private static void addPersonal(Resume resume) {
        SimpleTextSection sectionPersonal = new SimpleTextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addSection(ResumeSectionType.PERSONAL, sectionPersonal);
    }

    private static void addAchievement(Resume resume) {
        ListSection sectionAchievement = new ListSection();
        sectionAchievement.addSectionPart("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        sectionAchievement.addSectionPart("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        sectionAchievement.addSectionPart("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера");
        resume.addSection(ResumeSectionType.ACHIEVEMENT, sectionAchievement);
    }

    private static void addQualification(Resume resume) {
        ListSection sectionQualifications = new ListSection();
        sectionQualifications.addSectionPart("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        sectionQualifications.addSectionPart("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        sectionQualifications.addSectionPart("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера");
        resume.addSection(ResumeSectionType.QUALIFICATIONS, sectionQualifications);
    }

    private static void  addExperience(Resume resume) {
        OrganizationSection jobs = new OrganizationSection();
        Organization org1 = new Organization("Java Online Projects", "http://javaops.ru/");
        org1.addExperience(LocalDate.of(2014,10,1)
                , LocalDate.of(2016, 1, 1)
                , "Java архитектор"
                , "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        jobs.addOrganization(org1);
        Organization org2 = new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/");
        org2.addExperience(LocalDate.of(2010,12,1)
                , LocalDate.of(2012, 4, 1)
                , "Ведущий программист"
                , "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML");
        jobs.addOrganization(org2);
        resume.addSection(ResumeSectionType.EXPERIENCE, jobs);
    }

    private static void addEducation(Resume resume) {
        OrganizationSection educations = new OrganizationSection();
        Organization school1 = new Organization("Coursera", "https://www.coursera.org/course/progfun");
        school1.addExperience(LocalDate.of(2013,3,1)
                , LocalDate.of(2013, 5, 1)
                , ""
                , "\"Functional Programming Principles in Scala\" by Martin Odersky");
        educations.addOrganization(school1);
        Organization school2 = new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");
        school2.addExperience(LocalDate.of(2011,3,1)
                , LocalDate.of(2011, 4, 1)
                , ""
                , "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        educations.addOrganization(school2);
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
}
