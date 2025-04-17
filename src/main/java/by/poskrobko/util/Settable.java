package by.poskrobko.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface Settable {
    void set(PreparedStatement statement) throws SQLException;
}
