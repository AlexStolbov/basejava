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
            Map<SectionType, AbstractSection> sections = r.getSections();
            writeSimpleTextSection(sections.get(SectionType.OBJECTIVE), dos);
            writeSimpleTextSection(sections.get(SectionType.PERSONAL), dos);
            writeListSection(sections.get(SectionType.ACHIEVEMENT), dos);
            writeListSection(sections.get(SectionType.QUALIFICATIONS), dos);
            writeOrganizationSection(sections.get(SectionType.EXPERIENCE), dos);
            writeOrganizationSection(sections.get(SectionType.EDUCATION), dos);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readContacts(dis, resume);
            readSimpleTextSection(dis, resume, SectionType.OBJECTIVE);
            readSimpleTextSection(dis, resume, SectionType.PERSONAL);
            readListSection(dis, resume, SectionType.ACHIEVEMENT);
            readListSection(dis, resume, SectionType.QUALIFICATIONS);
            readOrganizationSection(dis, resume, SectionType.EXPERIENCE);
            readOrganizationSection(dis, resume, SectionType.EDUCATION);
            return resume;
        }
    }

    private void writeContacts(Resume r, DataOutputStream dos) throws IOException {
        writeCollections(r.getContacts().entrySet(), dos,
                el -> {
                    dos.writeUTF(el.getKey().name());
                    dos.writeUTF(el.getValue());
                });
    }

    private void readContacts(DataInputStream dis, Resume resume) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private void writeSimpleTextSection(AbstractSection sts, DataOutputStream dos) throws IOException {
        dos.writeUTF(sts.toString());
    }

    private void readSimpleTextSection(DataInputStream dis, Resume resume, SectionType st) throws IOException {
        resume.addSection(st, new SimpleTextSection(dis.readUTF()));
    }

    private void writeListSection(AbstractSection sts, DataOutputStream dos) throws IOException {
        writeCollections(((ListSection) sts).getParts(), dos, dos::writeUTF);
    }

    private void readListSection(DataInputStream dis, Resume resume, SectionType st) throws IOException {
        int size = dis.readInt();
        ListSection sts = new ListSection();
        for (int i = 0; i < size; i++) {
            sts.addSectionPart(dis.readUTF());
        }
        resume.addSection(st, sts);
    }

    private void writeOrganizationSection(AbstractSection sts, DataOutputStream dos) throws IOException {
        writeCollections(((OrganizationSection) sts).getOrganization(), dos,
                el -> {
                    dos.writeUTF(el.getName());
                    dos.writeUTF(el.getUrl());
                    writeExperiences(el, dos);
                });
    }

    private void readOrganizationSection(DataInputStream dis, Resume resume, SectionType st) throws IOException {
        int size = dis.readInt();
        OrganizationSection os = new OrganizationSection();
        for (int i = 0; i < size; i++) {
            Organization org = new Organization(dis.readUTF(), dis.readUTF());
            readExperiences(dis, org);
            os.addOrganization(org);
        }
        resume.addSection(st, os);
    }

    private void writeExperiences(Organization org, DataOutputStream dos) throws IOException {
        writeCollections(org.getExperiences(),
                dos, el -> {
                    dos.writeUTF(el.getDateStart().toString());
                    dos.writeUTF(el.getDateFinish().toString());
                    dos.writeUTF(el.getPosition());
                    dos.writeUTF(el.getDescription());
                });
    }

    private void readExperiences(DataInputStream dis, Organization org) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            Organization.Experience exp = new Organization.Experience(LocalDate.parse(dis.readUTF()),
                    LocalDate.parse(dis.readUTF()),
                    dis.readUTF(),
                    dis.readUTF());
            org.addExperience(exp);
        }
    }

    private <T> void writeCollections(Collection<T> coll, DataOutputStream dos, ConsumerException<T> toDo) throws IOException {
        dos.writeInt(coll.size());
        for (T element : coll) {
            toDo.accept(element);
        }
    }

    private interface ConsumerException<T> {
        void accept(T t) throws IOException;
    }

}
