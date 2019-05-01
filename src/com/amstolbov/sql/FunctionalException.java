package com.amstolbov.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FunctionalException<T>{
    T apply(ResultSet resultSet) throws SQLException;
}
