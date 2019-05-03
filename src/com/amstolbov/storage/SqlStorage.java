package com.amstolbov.storage;

import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.exception.StorageException;
import com.amstolbov.model.Resume;
import com.amstolbov.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        int updated = sqlHelper.executeSql("UPDATE resume SET full_name=? WHERE uuid=?",
                ps -> {
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, uuid);
                    return ps.executeUpdate();
                });
        if (updated == 0) {
            throw new NotExistStorageException(uuid);
        }
        LOG.info("Update resume " + uuid);
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        int inserted = sqlHelper.executeSql("INSERT INTO resume(uuid, full_name) VALUES (?, ?)",
                ps -> {
                    ps.setString(1, uuid);
                    ps.setString(2, resume.getFullName());
                    try {
                        return ps.executeUpdate();
                    } catch (SQLException e) {
                        throw new ExistStorageException(uuid);
                    }
                });
        if (inserted == 0) {
            throw new StorageException("Error save resume", uuid);
        }
        LOG.info("Clear storage");
    }

    @Override
    public Resume get(String uuid) {
        String fullName = sqlHelper.executeSql("SELECT r.full_name full_name FROM resume r WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ps.executeQuery();
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        return rs.getString("full_name");
                    }
                    return "";
                }
        );
        if (fullName.equals("")) {
            throw new NotExistStorageException(uuid);
        }
        return new Resume(uuid, fullName);

    }

    @Override
    public void delete(String uuid) {
        int deleted = sqlHelper.executeSql("DELETE FROM resume WHERE uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    return ps.executeUpdate();
                });
        if (deleted == 0) {
            throw new NotExistStorageException(uuid);
        }
        LOG.info("Delete resume " + uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeSql("SELECT r.uuid uuid, r.full_name full_name FROM resume r ORDER BY r.uuid",
                ps -> {
                    ArrayList<Resume> result = new ArrayList<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        result.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
                    }
                    return result;
                });
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
