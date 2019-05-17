package com.amstolbov.storage;

import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.model.*;
import com.amstolbov.sql.FunctionalException;
import com.amstolbov.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private final SqlHelper sqlHelper;


    public SqlStorage(final String dbUrl, final String dbUser, final String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeSql("DELETE FROM resume",
                PreparedStatement::execute);
        LOG.info("Clear storage");
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        sqlHelper.executeTransaction(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                        ps.setString(1, resume.getFullName());
                        ps.setString(2, uuid);
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(uuid);
                        }
                    }
                    clearTable(conn, "contact", uuid);
                    saveContacts(resume, conn);
                    clearTable(conn, "simplesection", uuid);
                    saveSimpleSections(resume, conn);
                    clearTable(conn, "listsection", uuid);
                    saveListSections(resume, conn);
                    return null;
                }
        );
        LOG.info("Update resume " + uuid);
    }

    private void clearTable(Connection conn, String tableName, String uuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tableName + " WHERE resume_uuid = ?")) {
            ps.setString(1, uuid);
            ps.executeUpdate();
        }
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();

        sqlHelper.executeTransaction(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "INSERT INTO resume(uuid, full_name) VALUES (?, ?)")) {
                        ps.setString(1, uuid);
                        ps.setString(2, resume.getFullName());
                        ps.executeUpdate();
                    }
                    saveContacts(resume, conn);
                    saveSimpleSections(resume, conn);
                    saveListSections(resume, conn);
                    return null;
                }
        );
        LOG.info("Clear storage");
    }

    private void saveContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                "INSERT INTO contact(resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> element : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, element.getKey().toString());
                ps.setString(3, element.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void saveSimpleSections(Resume resume, Connection conn) throws SQLException {
        saveTable(resume
                , conn
                , "INSERT INTO simplesection(resume_uuid, type, description) VALUES (?, ?, ?)"
                , Arrays.asList(SectionType.PERSONAL, SectionType.OBJECTIVE));
    }

    private void saveListSections(Resume resume, Connection conn) throws SQLException {
        saveTable(resume
                , conn
                , "INSERT INTO listsection(resume_uuid, type, part) VALUES (?, ?, ?)"
                , Arrays.asList(SectionType.ACHIEVEMENT, SectionType.QUALIFICATIONS));
    }

    private void saveTable(Resume resume, Connection conn, String queryText, List<SectionType> sections) throws SQLException {
        String uuid = resume.getUuid();
        try (PreparedStatement ps = conn.prepareStatement(queryText)) {
            for (Map.Entry<SectionType, AbstractSection> el : resume.getSections().entrySet()) {
                if (sections.contains(el.getKey())) {
                    ps.setString(1, uuid);
                    ps.setString(2, el.getKey().name());
                    ps.setString(3, el.getValue().toString());
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        }
    }

    @Override
    public Resume get(String uuid) {
        List<Resume> resumes = getDate(uuid);
        if (resumes.size() == 0) {
            throw new NotExistStorageException(uuid);
        }
        return resumes.get(0);
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeSql("DELETE FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
        LOG.info("Delete resume " + uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        return getDate(null);
    }

    private List<Resume> getDate(String uuidFilter) {
        String where = uuidFilter == null ? "" : " WHERE resume_uuid = ?";
        String whereR = where.replaceAll("resume_", "");
        return sqlHelper.executeTransaction(conn -> {
                    Map<String, Map<ContactType, String>> contacts = new HashMap<>();
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "SELECT resume_uuid, type, value FROM contact " + where)) {
                        if (!where.equals("")) {
                            ps.setString(1, uuidFilter);
                        }
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            String uuid = rs.getString("resume_uuid").trim();
                            Map<ContactType, String> ex = contacts.computeIfAbsent(uuid, key -> new EnumMap(ContactType.class));
                            ex.put(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                            contacts.put(uuid, ex);
                        }
                    }
                    Map<String, Map<SectionType, AbstractSection>> sections = new HashMap<>();
                    selectSection(conn,
                            "SELECT resume_uuid, type, description FROM simplesection " + where,
                            uuidFilter,
                            sections,
                            (rs -> new SimpleTextSection(rs.getString("description"))));
                    selectSection(conn,
                            "SELECT resume_uuid, type, part FROM listsection " + where,
                            uuidFilter,
                            sections,
                            (rs -> new ListSection(rs.getString("part"))));
                    List<Resume> result = new ArrayList<>();
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "SELECT uuid, full_name FROM resume " + whereR + " ORDER BY uuid")) {
                        if (!whereR.equals("")) {
                            ps.setString(1, uuidFilter);
                        }
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            Resume newResume = new Resume(rs.getString("uuid").trim(), rs.getString("full_name"));
                            newResume.addContacts(contacts.get(newResume.getUuid()));
                            newResume.addSections(sections.get(newResume.getUuid()));
                            result.add(newResume);
                        }
                    }
                    return result;
                }
        );
    }

    private void selectSection(Connection conn,
                               String queryText,
                               String uuidFilter,
                               Map<String, Map<SectionType, AbstractSection>> sections,
                               FunctionalException<AbstractSection, ResultSet> getSection) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(queryText)) {
            if (uuidFilter != null) {
                ps.setString(1, uuidFilter);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("resume_uuid").trim();
                sections.put(uuid, sections.computeIfAbsent(uuid, key -> new EnumMap(SectionType.class)));
                sections.get(uuid).put(SectionType.valueOf(rs.getString("type")), getSection.apply(rs));
            }
        }
    }

    @Override
    public int size() {
        return sqlHelper.executeSql("SELECT COUNT(*) size FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    return rs.getInt("size");
                });
    }
}
