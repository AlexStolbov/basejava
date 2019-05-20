package com.amstolbov.storage;

import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.exception.StorageException;
import com.amstolbov.model.*;
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
                    removeDependent(conn, "DELETE FROM contact WHERE resume_uuid = ?", uuid);
                    saveContacts(resume, conn);
                    removeDependent(conn, "DELETE FROM resumesection WHERE resume_uuid = ?", uuid);
                    saveSections(resume, conn);
                    return null;
                }
        );
        LOG.info("Update resume " + uuid);
    }

    private void removeDependent(Connection conn, String query, String uuid) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query)) {
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
                    saveSections(resume, conn);
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

    private void saveSections(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                "INSERT INTO resumesection(resume_uuid, type, description) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, AbstractSection> el : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, el.getKey().name());
                ps.setString(3, el.getValue().toString());
                ps.addBatch();
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
        return sqlHelper.executeTransaction(conn -> {
                    Map<String, Map<ContactType, String>> contacts = new HashMap<>();
                    try (PreparedStatement ps = conn.prepareStatement(getQuery("contact", uuidFilter))) {
                        if (uuidFilter != null) {
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
                            uuidFilter,
                            sections);
                    List<Resume> result = new ArrayList<>();
                    String queryR = "SELECT uuid, full_name FROM resume ";
                    if (uuidFilter != null) {
                        queryR = queryR.concat(" WHERE uuid = ?");
                    }
                    try (PreparedStatement ps = conn.prepareStatement(queryR + " ORDER BY uuid")) {
                        if (uuidFilter != null) {
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
                               String uuidFilter,
                               Map<String, Map<SectionType, AbstractSection>> sections) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(getQuery("resumesection", uuidFilter))) {
            if (uuidFilter != null) {
                ps.setString(1, uuidFilter);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("resume_uuid").trim();
                sections.put(uuid, sections.computeIfAbsent(uuid, key -> new EnumMap(SectionType.class)));
                SectionType sectionType = SectionType.valueOf(rs.getString("type"));
                sections.get(uuid).put(sectionType, createSection(sectionType, rs.getString("description")));
            }
        }
    }

    private String getQuery(String tableName, String uuidFilter) {
        String query;
        switch (tableName) {
            case "contact":
                query = "SELECT resume_uuid, type, value FROM contact";
                break;
            case "resumesection":
                query = "SELECT resume_uuid, type, description FROM resumesection";
                break;
            default:
                throw new StorageException("unknown table name", tableName);
        }
        if (uuidFilter != null) {
            query = query.concat(" WHERE resume_uuid = ?");
        }
        return query;
    }

    private AbstractSection createSection(SectionType sectionType, String description) {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new SimpleTextSection(description);
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(description);
            default:
                throw new StorageException("", "");
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
