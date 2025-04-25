package by.poskrobko.mapper;

import by.poskrobko.model.CEFRLevel;
import by.poskrobko.model.Grade;
import by.poskrobko.model.Language;

import java.sql.ResultSet;

public class GradeMapper extends BaseMapper<Grade> {

    public Grade toGrade(ResultSet resultSet) {
        return map(resultSet, rs -> {
            String id = resultSet.getString("grade_id");
            String language = resultSet.getString("language_name");
            String value = resultSet.getString("value");
            if (id == null || language == null || value == null) {
                return null;
            }
            return new Grade(id, new Language(language, "", ""), CEFRLevel.valueOf(value));
        });
    }
}
