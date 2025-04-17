package by.poskrobko.util;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLExecutor {

    public <R> R execute(SQLExecutable<R> executable) {
        try (Connection connection = DBManager.getConnection()) {
            return executable.execute(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeVoid(SQLVoidExecutable executable) {
        try (Connection connection = DBManager.getConnection()) {
            executable.execute(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
