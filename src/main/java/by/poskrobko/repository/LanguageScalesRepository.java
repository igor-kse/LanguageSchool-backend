package by.poskrobko.repository;

import by.poskrobko.model.LanguageScale;

import java.util.List;

public interface LanguageScalesRepository {

    LanguageScale findByName(String name);

    void save(LanguageScale languageScale);

    void update(LanguageScale languageScale);

    void delete(String name);

    List<LanguageScale> getAll();
}
