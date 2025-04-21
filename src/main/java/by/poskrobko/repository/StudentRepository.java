package by.poskrobko.repository;

import by.poskrobko.model.Student;

import java.util.List;

public interface StudentRepository {
    Student save(Student student);

    void update(Student student);

    void delete(String id);

    List<Student> getAllStudents();
}
