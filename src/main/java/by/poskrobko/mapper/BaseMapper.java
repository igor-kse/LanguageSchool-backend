package by.poskrobko.mapper;

import by.poskrobko.util.SQLMappable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseMapper<T> {

    protected T map(ResultSet resultSet, SQLMappable<T> mappable) {
        try {
            return mappable.extract(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> mapMultiple(ResultSet resultSet, SQLMappable<T> extractor) {
        try {
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = extractor.extract(resultSet);
                if (entity != null) {
                    entities.add(entity);
                }
            }
            return entities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
