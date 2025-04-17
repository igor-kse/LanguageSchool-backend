package by.poskrobko.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SQLMappable<T> {
    T extract(ResultSet resultSet) throws SQLException;
}
