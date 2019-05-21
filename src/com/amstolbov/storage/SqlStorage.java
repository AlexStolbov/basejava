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
        return sqlHelper.executeTransaction(conn -> {
                    Resume resume;
                    try (PreparedStatement ps = conn.prepareStatement("SELECT full_name FROM resume WHERE uuid = ?")) {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        if (!rs.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        resume = new Resume(uuid, rs.getString("full_name"));
                    }

                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "SELECT resume_uuid, type, value FROM contact WHERE resume_uuid = ?")) {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            addContact(resume, rs);
                        }
                    }

                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "SELECT resume_uuid, type, description FROM resumesection WHERE resume_uuid = ?")) {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            addSection(resume, rs);
                        }
                    }
                    return resume;
                }
        );
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
        return sqlHelper.executeTransaction(conn -> {
                    Map<String, Resume> result = new LinkedHashMap<>();
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "SELECT uuid, full_name FROM resume ORDER BY uuid")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            String uuid = rs.getString("uuid").trim();
                            result.put(uuid, new Resume(uuid, rs.getString("full_name")));
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "SELECT resume_uuid, type, value FROM contact")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            addContact(result.get(rs.getString("resume_uuid").trim()), rs);
                        }
                    }

                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "SELECT resume_uuid, type, description FROM resumesection")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            addSection(result.get(rs.getString("resume_uuid").trim()), rs);
                        }
                    }
                    return new ArrayList<>(result.values());
                }
        );
    }

    private void addContact(Resume r, ResultSet rs) throws SQLException {
        r.addContact(ContactType.valueOf(rs.getString("type")),
                rs.getString("value"));
    }

    private void addSection(Resume r, ResultSet rs) throws SQLException {
        SectionType st = SectionType.valueOf(rs.getString("type"));
        r.addSection(st, createSection(st, rs.getString("description")));
    }

    private AbstractSection createSection(SectionType sectionType, String description) {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return new SimpleTextSection(description);
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(description.split("\n"));
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
