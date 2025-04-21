package by.poskrobko.repository;

import by.poskrobko.repository.impl.StudentGradeDAOImpl;

import java.util.List;

public interface StudentGradeDAO {
    void save(StudentGradeDAOImpl.StudentGrade studentGrade);

    void delete(StudentGradeDAOImpl.StudentGrade studentGrade);

    List<StudentGradeDAOImpl.StudentGrade> findAllByUser(String id);

    List<StudentGradeDAOImpl.StudentGrade> findAllByGrade(String id);
}
