package by.poskrobko.repository.impl;

import by.poskrobko.model.Language;
import by.poskrobko.repository.LanguageRepository;
import by.poskrobko.util.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static by.poskrobko.TestData.*;

class LanguageRepositoryTest {

    private final LanguageRepository languageRepository = new LanguageRepositoryImpl();

    @BeforeEach
    public void setUp() {
        DBManager.dropDatabase();
        DBManager.initDatabase();
        languageRepository.save(ENGLISH);
        languageRepository.save(FRENCH);
        languageRepository.save(ITALIAN);
    }

    @Test
    public void save() {
        languageRepository.save(PORTUGUESE);
        Assertions.assertEquals(PORTUGUESE, languageRepository.findByName(PORTUGUESE.getName()));
    }

    @Test
    public void findByName() {
        Assertions.assertEquals(ENGLISH, languageRepository.findByName(ENGLISH.getName()));
        Assertions.assertEquals(FRENCH, languageRepository.findByName(FRENCH.getName()));
        Assertions.assertEquals(ITALIAN, languageRepository.findByName(ITALIAN.getName()));
    }

    @Test
    public void findAll() {
        List<Language> languages = languageRepository.findAll();
        Assertions.assertIterableEquals(List.of(ENGLISH, FRENCH, ITALIAN), languages);
    }

    @Test
    public void delete() {
        languageRepository.delete(ENGLISH.getName());
        Assertions.assertNull(languageRepository.findByName(ENGLISH.getName()));
        languageRepository.delete(FRENCH.getName());
        Assertions.assertNull(languageRepository.findByName(FRENCH.getName()));
        languageRepository.delete(ITALIAN.getName());
        Assertions.assertNull(languageRepository.findByName(ITALIAN.getName()));
    }
}