package com.amstolbov.storage;

import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.NotExistStorageException;
import com.amstolbov.model.Resume;
import com.amstolbov.sql.ConnectionFactory;
import com.amstolbov.sql.SqlHelper;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public SqlStorage(final String dbUrl, final String dbUser, final String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.executeSql(connectionFactory, "DELETE FROM resume");
        LOG.info("Clear storage");
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        checkExist(uuid);
        SqlHelper.executeSqlParams(connectionFactory,
                "UPDATE resume SET full_name=? WHERE uuid=?",
                ps -> {
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, uuid);
                });
        LOG.info("Update resume " + uuid);
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        checkNotExist(uuid);
        SqlHelper.executeSqlParams(connectionFactory,
                "INSERT INTO resume(uuid, full_name) VALUES (?, ?)",
                ps -> {
                    ps.setString(1, uuid);
                    ps.setString(2, resume.getFullName());
                });
        LOG.info("Clear storage");
    }

    @Override
    public Resume get(String uuid) {
        checkExist(uuid);
        String fullName = (String) SqlHelper.executeSqlParamsResult(connectionFactory,
                "SELECT r.full_name full_name FROM resume r WHERE r.uuid = ?",
                ps -> ps.setString(1, uuid),
                rs -> {
                    rs.next();
                    return rs.getString("full_name");
                }
        );
        return new Resume(uuid, fullName);
    }

    @Override
    public void delete(String uuid) {
        checkExist(uuid);
        SqlHelper.executeSqlParams(connectionFactory,
                "DELETE FROM resume WHERE uuid = ?",
                ps -> ps.setString(1, uuid));
        LOG.info("Delete resume " + uuid);
    }

    @Override
    public List<Resume> getAllSorted() {

        return  (List<Resume>) SqlHelper.executeSqlResult(connectionFactory,
                "SELECT r.uuid uuid, r.full_name full_name FROM resume r ORDER BY r.uuid",
                rs -> {
                    ArrayList<Resume> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
                    }
                    return result;
                });
    }

    @Override
    public int size() {
        return (int) SqlHelper.executeSqlResult(connectionFactory,
                "SELECT COUNT(*) size FROM resume",
                rs -> {
                    rs.next();
                    return rs.getInt("size");
                });
    }

    private void checkExist(String uuid) {
        if (!uuidIsExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void checkNotExist(String uuid) {
        if (uuidIsExist(uuid)) {
            throw new ExistStorageException(uuid);
        }
    }

    private boolean uuidIsExist(String uuid) {
        return (boolean) SqlHelper.executeSqlParamsResult(connectionFactory,
                "SELECT * FROM resume WHERE uuid = ?",
                ps -> ps.setString(1, uuid),
                rs -> {
                    if (rs.next()) {
                        return true;
                    } else {
                        return false;
                    }
                });
    }
}
