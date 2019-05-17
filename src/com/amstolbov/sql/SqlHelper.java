package com.amstolbov.sql;

import com.amstolbov.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(final String dbUrl, final String dbUser, final String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T executeSql(String sqlStatement, FunctionalException<T, PreparedStatement> getResult) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlStatement)) {
            return getResult.apply(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T executeTransaction(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionalUtil.convertExceptional(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }

}
