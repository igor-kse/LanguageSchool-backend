package by.poskrobko.repository.impl;

import by.poskrobko.model.Student;
import by.poskrobko.repository.StudentRepository;
import by.poskrobko.repository.UserRepository;
import by.poskrobko.util.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static by.poskrobko.TestData.*;

class StudentRepositoryTest {

    private final StudentRepository studentRepository = new StudentRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    @BeforeEach
    void setUp() {
        DBManager.dropDatabase();
        DBManager.initDatabase();
        userRepository.save(USER_3);
        userRepository.save(USER_4);
        studentRepository.save(STUDENT_3);
        studentRepository.save(STUDENT_4);
    }

    @Test
    void save() {
        userRepository.save(USER_1);
        studentRepository.save(STUDENT_1);
        Student actual = studentRepository.findById(USER_UUID_1);
        Assertions.assertEquals(STUDENT_1, actual);
    }

    @Test
    void findById() {
        Student actual = studentRepository.findById(USER_UUID_3);
        Assertions.assertEquals(STUDENT_3, actual);
    }

    @Test
    void getAllStudents() {
        List<Student> actual = studentRepository.getAllStudents();
        Assertions.assertIterableEquals(List.of(STUDENT_3, STUDENT_4), actual);
    }

    @Test
    void update() {
        Student student = studentRepository.findById(USER_UUID_3);
        student.setAge(25);
        student.setChannel("discord");
        student.setHobbies("gaming");
        student.setNote("note");
        studentRepository.update(student);
        Assertions.assertEquals(student, studentRepository.findById(USER_UUID_3));
    }

    @Test
    void delete() {
        studentRepository.delete(USER_UUID_3);
        Assertions.assertNull(studentRepository.findById(USER_UUID_3));
    }
}
