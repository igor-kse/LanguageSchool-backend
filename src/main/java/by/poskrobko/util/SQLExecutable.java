package by.poskrobko.util;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLExecutable<R> {
    R execute(Connection connection) throws SQLException;
}
