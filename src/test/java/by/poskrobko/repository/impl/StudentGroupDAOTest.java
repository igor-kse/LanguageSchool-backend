package by.poskrobko.repository.impl;

import by.poskrobko.model.Role;
import by.poskrobko.model.User;
import by.poskrobko.repository.GroupRepository;
import by.poskrobko.repository.TeacherRepository;
import by.poskrobko.repository.UserRepository;
import by.poskrobko.repository.impl.StudentGroupDAOImpl.StudentGroup;
import by.poskrobko.util.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static by.poskrobko.TestData.*;

class StudentGroupDAOTest {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final TeacherRepository teacherRepository = new TeacherRepositoryImpl();
    private final GroupRepository groupRepository = new GroupRepositoryImpl();
    private final StudentGroupDAOImpl studentGroupDAO = new StudentGroupDAOImpl();

    @BeforeEach
    void setUp() {
        DBManager.dropDatabase();
        DBManager.initDatabase();
        userRepository.save(TEACHER.getUser());
        teacherRepository.save(TEACHER);
        userRepository.save(USER_3); // student
        userRepository.save(USER_4); // student
        groupRepository.save(GROUP_1);
        studentGroupDAO.save(USER_3_GROUP_1);
        studentGroupDAO.save(USER_4_GROUP_1);
    }

    @Test
    void save() {
        User user = new User("user_to_save", "the name", "the surname", "the_email@email.com",
                "password", Set.of(Role.USER, Role.STUDENT), null);
        StudentGroup expected = new StudentGroup(user.getUserId(), GROUP_UUID_1);
        userRepository.save(user);
        studentGroupDAO.save(expected);
        StudentGroup actual = studentGroupDAO.findAllByStudentId(user.getUserId()).stream().findFirst().orElseThrow();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllByStudentId() {
        List<StudentGroup> expected = List.of(USER_3_GROUP_1, USER_4_GROUP_1);
        List<StudentGroup> actual = studentGroupDAO.findAllByGroupId(GROUP_UUID_1);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void findAllByGroupId() {
        List<StudentGroup> expected = List.of(USER_3_GROUP_1);
        List<StudentGroup> actual = studentGroupDAO.findAllByStudentId(USER_UUID_3);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void deleteByStudentId() {
        studentGroupDAO.deleteByStudentId(USER_UUID_3);
        List<StudentGroup> expected = List.of(USER_4_GROUP_1);
        List<StudentGroup> actual = studentGroupDAO.findAllByGroupId(GROUP_UUID_1);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void deleteByGroupId() {
        studentGroupDAO.deleteByGroupId(GROUP_UUID_1);
        List<StudentGroup> expected = List.of();
        List<StudentGroup> actual = studentGroupDAO.findAllByGroupId(GROUP_UUID_1);
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    void deleteByStudentIdAndGroupId() {
        studentGroupDAO.deleteByStudentAndGroupId(USER_UUID_3, GROUP_UUID_1);
        List<StudentGroup> expected = List.of(USER_4_GROUP_1);
        List<StudentGroup> actual = studentGroupDAO.findAllByGroupId(GROUP_UUID_1);
        Assertions.assertIterableEquals(expected, actual);
    }
}