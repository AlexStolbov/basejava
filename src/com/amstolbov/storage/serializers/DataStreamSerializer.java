package com.amstolbov.storage.serializers;

import com.amstolbov.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeContacts(r, dos);
            dos.writeInt(r.getSections().size());
            for (Map.Entry<SectionType, AbstractSection> sec : r.getSections().entrySet()) {
                dos.writeUTF(sec.getKey().toString());
                switch (sec.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        writeSimpleTextSection(sec.getValue(), dos);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeListSection(sec.getValue(), dos);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeOrganizationSection(sec.getValue(), dos);
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readContacts(dis, resume);
            int sectionsCount = dis.readInt();
            for (int i = 0; i < sectionsCount; i++) {
                readSection(dis, resume);
            }
            return resume;
        }
    }

    private void writeContacts(Resume r, DataOutputStream dos) throws IOException {
        writeCollection(r.getContacts().entrySet(), dos,
                el -> {
                    dos.writeUTF(el.getKey().name());
                    dos.writeUTF(el.getValue());
                });
    }

    private void writeSimpleTextSection(AbstractSection sts, DataOutputStream dos) throws IOException {
        dos.writeUTF(sts.toString());
    }

    private void writeListSection(AbstractSection sts, DataOutputStream dos) throws IOException {
        writeCollection(((ListSection) sts).getParts(), dos, dos::writeUTF);
    }

    private void writeOrganizationSection(AbstractSection sts, DataOutputStream dos) throws IOException {
        writeCollection(((OrganizationSection) sts).getOrganization(), dos,
                el -> {
                    dos.writeUTF(el.getName());
                    dos.writeUTF(convertNull(el.getUrl()));
                    writeExperiences(el, dos);
                });
    }

    private void writeExperiences(Organization org, DataOutputStream dos) throws IOException {
        writeCollection(org.getExperiences(),
                dos, el -> {
                    dos.writeUTF(el.getDateStart().toString());
                    dos.writeUTF(el.getDateFinish().toString());
                    dos.writeUTF(el.getPosition());
                    dos.writeUTF(convertNull(el.getDescription()));
                });
    }

    private <T> void writeCollection(Collection<T> coll, DataOutputStream dos, ConsumerException<T> toDo) throws IOException {
        dos.writeInt(coll.size());
        for (T element : coll) {
            toDo.accept(element);
        }
    }

    private interface ConsumerException<T> {
        void accept(T t) throws IOException;
    }

    private void readContacts(DataInputStream dis, Resume resume) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private void readSection(DataInputStream dis, Resume resume) throws IOException {
        String sectionName = dis.readUTF();
        SectionType currentSectionType = SectionType.valueOf(sectionName);
        switch (currentSectionType) {
            case OBJECTIVE:
            case PERSONAL:
                readSimpleTextSection(dis, resume, currentSectionType);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                readListSection(dis, resume, currentSectionType);
                break;
            case EXPERIENCE:
            case EDUCATION:
                readOrganizationSection(dis, resume, currentSectionType);
        }
    }

    private void readSimpleTextSection(DataInputStream dis, Resume resume, SectionType st) throws IOException {
        resume.addSection(st, new SimpleTextSection(dis.readUTF()));
    }

    private void readListSection(DataInputStream dis, Resume resume, SectionType st) throws IOException {
        ListSection sts = new ListSection();
        readCollection(dis, sts, ((el1, el2) -> el2.addSectionPart(el1.readUTF())));
        resume.addSection(st, sts);
    }

    private void readOrganizationSection(DataInputStream dis, Resume resume, SectionType st) throws IOException {
        OrganizationSection os = new OrganizationSection();
        readCollection(dis, os, ((el1, el2) -> {
            Organization org = new Organization(el1.readUTF(), convertNull(el1.readUTF()));
            readExperiences(el1, org);
            el2.addOrganization(org);
        }));
        resume.addSection(st, os);
    }

    private void readExperiences(DataInputStream dis, Organization org) throws IOException {
        readCollection(dis, org, ((el1, el2) -> {
            Organization.Experience exp = new Organization.Experience(
                    LocalDate.parse(el1.readUTF()),
                    LocalDate.parse(el1.readUTF()),
                    el1.readUTF(),
                    convertNull(el1.readUTF()));
            el2.addExperience(exp);
        }));
    }

    private <T> void readCollection(DataInputStream dis, T st, DisConsumerException<T> cons) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            cons.accept(dis, st);
        }
    }

    private interface DisConsumerException<T> {
        void accept(DataInputStream t, T u) throws IOException;
    }

    private String convertNull(String converted) {
        String nullString = "<<null>>";
        if (converted == null) {
            return nullString;
        }
        if (converted.equals(nullString)) {
            return null;
        }
        return converted;
    }

}
