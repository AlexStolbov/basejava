package com.amstolbov.sql;

import com.amstolbov.exception.ExistStorageException;
import com.amstolbov.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionalUtil {

    public static StorageException convertExceptional(SQLException e) {
        if (e instanceof PSQLException) {
            //http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
            if (e.getSQLState().equals("23505")) {
                return new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}
