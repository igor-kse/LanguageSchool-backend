package by.poskrobko.repository.impl;

import by.poskrobko.model.Teacher;
import by.poskrobko.repository.TeacherRepository;
import by.poskrobko.repository.UserRepository;
import by.poskrobko.util.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static by.poskrobko.TestData.*;

class TeacherRepositoryTest {

    private final TeacherRepository teacherRepository = new TeacherRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    @BeforeEach
    public void setUp() {
        DBManager.dropDatabase();
        DBManager.initDatabase();
        userRepository.save(USER_1);
        userRepository.save(USER_2);
        userRepository.save(USER_3);
        userRepository.save(USER_4);
        teacherRepository.save(TEACHER);
    }

    @Test
    public void save() {
        Teacher teacher = new Teacher(USER_4, "University of Cambridge");
        teacherRepository.save(teacher);
        Assertions.assertEquals(teacher, teacherRepository.findById(teacher.getId()));
    }

    @Test
    public void findById() {
        Assertions.assertEquals(TEACHER, teacherRepository.findById(TEACHER.getId()));
    }

    @Test
    public void findAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        Assertions.assertEquals(TEACHER, teachers.getFirst());
    }

    @Test
    public void update() {
        Teacher teacher = teacherRepository.findById(TEACHER.getId());
        teacher.setEducation("University of Yale");
        teacherRepository.update(teacher);
        Assertions.assertEquals(teacher, teacherRepository.findById(teacher.getId()));
    }

    @Test
    public void delete() {
        Teacher teacher = teacherRepository.findById(TEACHER.getId());
        teacherRepository.delete(teacher.getId());
        Assertions.assertNull(teacherRepository.findById(teacher.getId()));
    }
}