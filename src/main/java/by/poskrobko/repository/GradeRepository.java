package by.poskrobko.repository;

import by.poskrobko.model.CEFRLevel;
import by.poskrobko.model.Grade;

public interface GradeRepository {
    void save(Grade grade);

    void delete(String id);

    Grade findById(String id);

    Grade findByLanguageAndLevel(String language, CEFRLevel level);
}
