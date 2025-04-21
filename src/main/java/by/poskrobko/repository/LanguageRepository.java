package by.poskrobko.repository;

import by.poskrobko.model.Language;

import java.util.List;

public interface LanguageRepository {
    void save(Language language);

    Language findByName(String name);

    List<Language> findAll();

    void delete(String name);

    void update(Language language);
}
