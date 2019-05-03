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

    public <T> T executeSql(String sqlStatement, FunctionalException<T> getResult) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlStatement)) {
            return getResult.apply(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
