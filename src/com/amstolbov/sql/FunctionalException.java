package com.amstolbov.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface FunctionalException<T>{
    T apply(PreparedStatement ps) throws SQLException;
}
