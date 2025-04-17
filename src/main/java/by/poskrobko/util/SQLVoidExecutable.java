package by.poskrobko.util;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLVoidExecutable {
    void execute(Connection connection) throws SQLException;
}
