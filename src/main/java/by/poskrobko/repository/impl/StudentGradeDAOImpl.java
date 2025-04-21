package by.poskrobko.repository.impl;

import by.poskrobko.mapper.StudentGradeMapper;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.StudentGradeDAO;

import java.util.List;

public class StudentGradeDAOImpl extends AbstractBaseDAO<StudentGradeDAOImpl.StudentGrade> implements StudentGradeDAO {

    private final StudentGradeMapper mapper = new StudentGradeMapper();

    @Override
    public void save(StudentGrade studentGrade) {
        doSave("INSERT INTO student_grade VALUES(?,?)",
                statement -> {
                    statement.setString(1, studentGrade.studentId);
                    statement.setString(2, studentGrade.gradeId);
                });
    }

    @Override
    public void delete(StudentGrade studentGrade) {
        doDeleteBySettable("DELETE FROM student_grade WHERE user_id = ? AND grade_id = ?",
                statement -> {
                    statement.setString(1, studentGrade.studentId);
                    statement.setString(2, studentGrade.gradeId);
                });
    }

    @Override
    public List<StudentGrade> findAllByUser(String id) {
        return doFindAllBy("SELECT user_id, grade_id FROM student_grade WHERE user_id = ?",
                statement -> statement.setString(1, id), mapper::toStudentGrade);
    }

    @Override
    public List<StudentGrade> findAllByGrade(String id) {
        return doFindAllBy("SELECT user_id, grade_id FROM student_grade WHERE grade_id = ?",
                statement -> statement.setString(1, id), mapper::toStudentGrade);
    }

    public record StudentGrade(String studentId, String gradeId) {
    }
}
