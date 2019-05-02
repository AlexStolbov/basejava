package com.amstolbov.sql;

import com.amstolbov.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public static <T> T executeSqlParamsResult(ConnectionFactory connectionFactory,
                                                String sqlStatement,
                                                ConsumerException setParameters,
                                                FunctionalException<T> getResult) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlStatement)) {
            setParameters.accept(ps);
            ps.execute();
            return getResult.apply(ps.getResultSet());
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public static void executeSqlParams(ConnectionFactory connectionFactory,
                                        String sqlStatement,
                                        ConsumerException setParameters) {
        executeSqlParamsResult(connectionFactory,
                sqlStatement,
                setParameters,
                rs -> null);
    }

    public static <T> T executeSqlResult(ConnectionFactory connectionFactory,
                                        String sqlStatement,
                                        FunctionalException<T> getResult) {
        return executeSqlParamsResult(connectionFactory,
                sqlStatement,
                ps -> {},
                getResult);
    }

    public static void executeSql(ConnectionFactory connectionFactory,
                                  String sqlStatement) {
        executeSqlParamsResult(connectionFactory,
                sqlStatement,
                ps -> {},
                rs -> null);
    }

}
