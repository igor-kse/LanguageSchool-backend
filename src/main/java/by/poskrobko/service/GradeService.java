package by.poskrobko.service;

import by.poskrobko.model.CEFRLevel;
import by.poskrobko.model.Grade;
import by.poskrobko.model.Language;
import by.poskrobko.repository.GradeRepository;
import by.poskrobko.repository.LanguageRepository;
import by.poskrobko.repository.impl.GradeRepositoryImpl;
import by.poskrobko.repository.impl.LanguageRepositoryImpl;
import by.poskrobko.util.Util;

import java.util.Objects;

public class GradeService {
    private final GradeRepository gradeRepository = new GradeRepositoryImpl();
    private final LanguageRepository languageRepository = new LanguageRepositoryImpl();

    public void create(String languageName, String level) {
        Language language = languageRepository.findByName(languageName);

        // Might be refactored if the language entity is extended
        if (language == null) {
            language = new Language(languageName, "", "");
            languageRepository.save(language);
        }
        Grade grade = new Grade(language, CEFRLevel.valueOf(level));
        gradeRepository.save(grade);
    }

    public void delete(String uuid) {
        Util.assureStringHasLength(uuid, "grade id");
        gradeRepository.delete(uuid);
    }

    public Grade findByUuid(String uuid) {
        return gradeRepository.findById(uuid);
    }

    public Grade findByNameAndLevel(String languageName, String level) {
        Util.assureStringHasLength(languageName, "language name");
        Util.assureStringHasLength(level, "CEFR level");

        Grade grade = gradeRepository.findByLanguageAndLevel(languageName, CEFRLevel.valueOf(level));
        Objects.requireNonNull(grade, String.format("Grade %s %s not found", languageName, level));

        return grade;
    }
}
