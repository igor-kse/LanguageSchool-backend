package by.poskrobko.repository.impl;

import by.poskrobko.model.CEFRLevel;
import by.poskrobko.repository.GradeRepository;
import by.poskrobko.repository.LanguageRepository;
import by.poskrobko.util.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static by.poskrobko.TestData.*;

class GradeRepositoryTest {

    private final LanguageRepository languageRepository = new LanguageRepositoryImpl();
    private final GradeRepository gradeRepository = new GradeRepositoryImpl();

    @BeforeEach
    public void setUp() {
        DBManager.dropDatabase();
        DBManager.initDatabase();
        languageRepository.save(ENGLISH);
        gradeRepository.save(GRADE_ENGLISH_A1);
        gradeRepository.save(GRADE_ENGLISH_A2);
        gradeRepository.save(GRADE_ENGLISH_B1);
    }

    @Test
    public void save() {
        gradeRepository.save(GRADE_ENGLISH_B2);
        Assertions.assertEquals(gradeRepository.findByLanguageAndLevel(ENGLISH.getName(), CEFRLevel.B2), GRADE_ENGLISH_B2);
    }

    @Test
    public void delete() {
        gradeRepository.delete(GRADE_UUID_ENGLISH_A1);
        Assertions.assertNull(gradeRepository.findByLanguageAndLevel(ENGLISH.getName(), CEFRLevel.A1));
        gradeRepository.delete(GRADE_UUID_ENGLISH_A2);
        Assertions.assertNull(gradeRepository.findByLanguageAndLevel(ENGLISH.getName(), CEFRLevel.A2));
        gradeRepository.delete(GRADE_UUID_ENGLISH_B1);
        Assertions.assertNull(gradeRepository.findByLanguageAndLevel(ENGLISH.getName(), CEFRLevel.B1));
    }

    @Test
    public void findByLanguageAndLevel() {
        Assertions.assertEquals(GRADE_ENGLISH_A1, gradeRepository.findByLanguageAndLevel(ENGLISH.getName(), CEFRLevel.A1));
        Assertions.assertEquals(GRADE_ENGLISH_A2, gradeRepository.findByLanguageAndLevel(ENGLISH.getName(), CEFRLevel.A2));
        Assertions.assertEquals(GRADE_ENGLISH_B1, gradeRepository.findByLanguageAndLevel(ENGLISH.getName(), CEFRLevel.B1));
    }

    @Test
    public void findById() {
        Assertions.assertEquals(GRADE_ENGLISH_A1, gradeRepository.findById(GRADE_UUID_ENGLISH_A1));
        Assertions.assertEquals(GRADE_ENGLISH_A2, gradeRepository.findById(GRADE_UUID_ENGLISH_A2));
        Assertions.assertEquals(GRADE_ENGLISH_B1, gradeRepository.findById(GRADE_UUID_ENGLISH_B1));
    }
}