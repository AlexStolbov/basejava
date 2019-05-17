package com.amstolbov.sql;

import java.sql.SQLException;

public interface FunctionalException<T, V>{
    T apply(V ps) throws SQLException;
}
