package com.amstolbov.storage.serializers;

import com.amstolbov.exception.StorageException;
import com.amstolbov.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeContacts(resume, dos);
            writeCollection(resume.getSections().entrySet(), dos, el -> {
                dos.writeUTF(el.getKey().toString());
                switch (el.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(el.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(((ListSection) el.getValue()).getParts(), dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeOrganizationSection(el.getValue(), dos);
                }
            });
        }
    }

    private void writeContacts(Resume resume, DataOutputStream dos) throws IOException {
        writeCollection(resume.getContacts().entrySet(), dos,
                el -> {
                    dos.writeUTF(el.getKey().name());
                    dos.writeUTF(el.getValue());
                });
    }

    private void writeOrganizationSection(AbstractSection abstractSection, DataOutputStream dos) throws IOException {
        writeCollection(((OrganizationSection) abstractSection).getOrganization(), dos,
                el -> {
                    dos.writeUTF(el.getName());
                    dos.writeUTF(nullableToWrite(el.getUrl()));
                    writeExperiences(el, dos);
                });
    }

    private void writeExperiences(Organization organization, DataOutputStream dos) throws IOException {
        writeCollection(organization.getExperiences(),
                dos, el -> {
                    dos.writeUTF(el.getDateStart().toString());
                    dos.writeUTF(el.getDateFinish().toString());
                    dos.writeUTF(el.getPosition());
                    dos.writeUTF(nullableToWrite(el.getDescription()));
                });
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream dos, WritingConsumerException<T> toDo) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            toDo.accept(element);
        }
    }

    private interface WritingConsumerException<T> {
        void accept(T t) throws IOException;
    }

    private String nullableToWrite(String converted) {
        if (converted == null) {
            return "";
        }
        return converted;
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readSectionsBlock(dis, resume, (elDis, elResume) -> elResume.addContact(ContactType.valueOf(elDis.readUTF()), elDis.readUTF()));
            readSectionsBlock(dis, resume, (elDis, elResume) -> {
                SectionType currentSectionType = SectionType.valueOf(dis.readUTF());
                elResume.addSection(currentSectionType, readSection(elDis, currentSectionType));
            });
            return resume;
        }
    }

    private void readSectionsBlock(DataInputStream dis, Resume resume, ReadBlock readBlock) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            readBlock.accept(dis, resume);
        }
    }

    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new SimpleTextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return readCollection(dis, new ListSection(), ((el1, el2) -> el2.addSectionPart(el1.readUTF())));
            case EXPERIENCE:
            case EDUCATION:
                return readOrganizationSection(dis);
            default: throw new StorageException("", "");
        }
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        return readCollection(dis, new OrganizationSection(), ((el1, el2) -> {
            Organization org = new Organization(el1.readUTF(), readNullable(el1.readUTF()));
            el2.addOrganization(readExperiences(el1, org));
        }));
    }

    private Organization readExperiences(DataInputStream dis, Organization organization) throws IOException {
        return readCollection(dis, organization, ((el1, el2) -> {
            Organization.Experience exp = new Organization.Experience(
                    LocalDate.parse(el1.readUTF()),
                    LocalDate.parse(el1.readUTF()),
                    el1.readUTF(),
                    readNullable(el1.readUTF()));
            el2.addExperience(exp);
        }));
    }

    private <T> T readCollection(DataInputStream dis, T collectionOwner, ReadingConsumerException<T> cons) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            cons.accept(dis, collectionOwner);
        }
        return collectionOwner;
    }

    private interface ReadBlock {
        void accept(DataInputStream t, Resume resume) throws IOException;
    }

    private interface ReadingConsumerException<T> {
        void accept(DataInputStream t, T sectionType) throws IOException;
    }

    private String readNullable(String converted) {
        if (converted.equals("")) {
            return null;
        }
        return converted;
    }

}
