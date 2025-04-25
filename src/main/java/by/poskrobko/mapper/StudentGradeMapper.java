package by.poskrobko.mapper;

import by.poskrobko.repository.impl.StudentGradeDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentGradeMapper extends BaseMapper<StudentGradeDAOImpl.StudentGrade> {

    public StudentGradeDAOImpl.StudentGrade toStudentGrade(ResultSet resultSet) throws SQLException {
        return map(resultSet, rs -> {
            String userId = resultSet.getString("user_id");
            String gradeId = resultSet.getString("grade_id");
            if (userId == null || gradeId == null) {
                return null;
            }
            return new StudentGradeDAOImpl.StudentGrade(userId, gradeId);
        });
    }
}
