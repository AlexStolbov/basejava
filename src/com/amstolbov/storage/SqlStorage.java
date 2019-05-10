package com.amstolbov.storage;

import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.model.ContactType;
import com.amstolbov.model.Resume;
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
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "DELETE FROM contact WHERE resume_uuid = ?")) {
                        ps.setString(1, uuid);
                        ps.executeUpdate();
                    }
                    insertContacts(resume, uuid, conn);
                    return null;
                }
        );
        LOG.info("Update resume " + uuid);
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
                    insertContacts(resume, uuid, conn);
                    return null;
                }
        );
        LOG.info("Clear storage");
    }

    private void insertContacts(Resume resume, String uuid, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("" +
                "INSERT INTO contact(resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> element : resume.getContacts().entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, element.getKey().toString());
                ps.setString(3, element.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeSql(" " +
                        "SELECT " +
                        "  r.full_name full_name " +
                        " ,cont.type contactType " +
                        " ,cont.value contactValue " +
                        "FROM resume r " +
                        "LEFT JOIN contact cont ON r.uuid = cont.resume_uuid " +
                        "WHERE r.uuid = ? ",
                ps -> {
                    ps.setString(1, uuid);
                    ps.executeQuery();
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        resume.addContact(ContactType.valueOf(rs.getString("contactType"))
                                , rs.getString("contactValue"));
                    } while (rs.next());
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
                    List<Resume> result = new ArrayList<>();
                    Map<String, Map<ContactType, String>> contacts = new HashMap<>();
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "SELECT cont.resume_uuid, cont.type, cont.value FROM contact cont")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            String uuid = rs.getString("resume_uuid").trim();
                            Map<ContactType, String> ex = contacts.get(uuid);
                            if (ex == null) {
                                ex = new EnumMap(ContactType.class);
                            }
                            ex.put(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                            contacts.put(uuid, ex);
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "SELECT r.uuid, r.full_name FROM resume r ORDER BY r.uuid")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            Resume r = new Resume(rs.getString("uuid").trim(), rs.getString("full_name"));
                            Map<ContactType, String> ex = contacts.get(r.getUuid());
                            if (ex != null) {
                                for (Map.Entry<ContactType, String> c : ex.entrySet()) {
                                    r.addContact(c.getKey(), c.getValue());
                                }
                            }
                            result.add(r);
                        }
                    }
                    return result;
                }
        );
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
