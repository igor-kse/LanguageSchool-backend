package by.poskrobko.repository.impl;

import by.poskrobko.model.Group;
import by.poskrobko.model.Teacher;
import by.poskrobko.repository.GradeRepository;
import by.poskrobko.repository.TeacherRepository;
import by.poskrobko.repository.UserRepository;
import by.poskrobko.util.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static by.poskrobko.TestData.*;

class GroupRepositoryTest {

    private final GroupRepositoryImpl groupRepository = new GroupRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final GradeRepository gradeRepository = new GradeRepositoryImpl();
    private final TeacherRepository teacherRepository = new TeacherRepositoryImpl();

    @BeforeEach
    public void setUp() {
        DBManager.dropDatabase();
        DBManager.initDatabase();
        userRepository.save(TEACHER.getUser());
        teacherRepository.save(TEACHER);
        gradeRepository.save(GRADE_ENGLISH_A1);
        gradeRepository.save(GRADE_ENGLISH_A2);
        groupRepository.save(GROUP_1);
    }

    @Test
    void save() {
        groupRepository.save(GROUP_2);
        Group actual = groupRepository.findById(GROUP_UUID_2);
        Assertions.assertEquals(GROUP_2, actual);
    }

    @Test
    void findById() {
        Group actual = groupRepository.findById(GROUP_UUID_1);
        Assertions.assertEquals(GROUP_1, actual);
    }

    @Test
    void update() {
        Teacher teacher = new Teacher(USER_3, "UofT");
        userRepository.save(USER_3);
        teacherRepository.save(teacher);

        Group group = groupRepository.findById(GROUP_UUID_1);
        group.setName("new name");
        group.setGrade(GRADE_ENGLISH_B2);
        group.setTeacher(teacher);
        groupRepository.update(group);
        Assertions.assertEquals(group, groupRepository.findById(group.getId()));
    }

    @Test
    void delete() {
        Group group = groupRepository.findById(GROUP_UUID_1);
        Assertions.assertNotNull(group);
        groupRepository.delete(GROUP_UUID_1);
        Assertions.assertNull(groupRepository.findById(GROUP_UUID_1));
    }
}