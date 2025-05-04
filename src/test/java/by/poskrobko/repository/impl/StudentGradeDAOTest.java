package by.poskrobko.repository.impl;

import by.poskrobko.repository.GradeRepository;
import by.poskrobko.repository.StudentGradeDAO;
import by.poskrobko.repository.UserRepository;
import by.poskrobko.util.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static by.poskrobko.TestData.*;

class StudentGradeDAOTest {

    private static final StudentGradeDAO studentGradeDAO = new StudentGradeDAOImpl();
    private static final UserRepository userRepository = new UserRepositoryImpl();
    private static final GradeRepository gradeRepository = new GradeRepositoryImpl();

    @BeforeEach
    public void setUp() {
        DBManager.dropDatabase();
        DBManager.initDatabase();

        userRepository.save(USER_1);
        userRepository.save(USER_2);
        userRepository.save(USER_3);

        gradeRepository.save(GRADE_ENGLISH_A1);
        gradeRepository.save(GRADE_ENGLISH_A2);
        gradeRepository.save(GRADE_ENGLISH_B1);
        gradeRepository.save(GRADE_ENGLISH_B2);

        studentGradeDAO.save(USER_1_ENGLISH_A1);
        studentGradeDAO.save(USER_2_ENGLISH_A2);
        studentGradeDAO.save(USER_3_ENGLISH_B1);
        studentGradeDAO.save(USER_3_ENGLISH_B2);
    }

    @Test
    public void save() {
        studentGradeDAO.save(USER_1_ENGLISH_B2);
        StudentGradeDAOImpl.StudentGrade actual = studentGradeDAO.findAllByUser(USER_UUID_1)
                .stream()
                .filter(sg -> sg.gradeId().equals(GRADE_UUID_ENGLISH_B2))
                .findFirst()
                .orElseThrow();
        Assertions.assertEquals(USER_1_ENGLISH_B2, actual);
    }

    @Test
    void findAllByUser() {
        studentGradeDAO.findAllByUser(USER_UUID_1);
        Assertions.assertIterableEquals(List.of(USER_1_ENGLISH_A1), studentGradeDAO.findAllByUser(USER_UUID_1));
        studentGradeDAO.findAllByUser(USER_UUID_2);
        Assertions.assertIterableEquals(List.of(USER_2_ENGLISH_A2), studentGradeDAO.findAllByUser(USER_UUID_2));
        studentGradeDAO.findAllByUser(USER_UUID_3);
        Assertions.assertIterableEquals(List.of(USER_3_ENGLISH_B1, USER_3_ENGLISH_B2), studentGradeDAO.findAllByUser(USER_UUID_3));
    }

    @Test
    void findAllByGrade() {
        studentGradeDAO.findAllByGrade(GRADE_UUID_ENGLISH_A1);
        Assertions.assertIterableEquals(List.of(USER_1_ENGLISH_A1), studentGradeDAO.findAllByGrade(GRADE_UUID_ENGLISH_A1));
        studentGradeDAO.findAllByUser(USER_UUID_2);
        Assertions.assertIterableEquals(List.of(USER_2_ENGLISH_A2), studentGradeDAO.findAllByGrade(GRADE_UUID_ENGLISH_A2));
        studentGradeDAO.findAllByUser(USER_UUID_3);
        Assertions.assertIterableEquals(List.of(USER_3_ENGLISH_B1), studentGradeDAO.findAllByGrade(GRADE_UUID_ENGLISH_B1));
    }
}