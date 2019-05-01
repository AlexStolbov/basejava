package com.amstolbov.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ConsumerException {
    void accept(PreparedStatement ps) throws SQLException;
}
