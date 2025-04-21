package by.poskrobko.repository.impl;

import by.poskrobko.mapper.GradeMapper;
import by.poskrobko.model.CEFRLevel;
import by.poskrobko.model.Grade;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.GradeRepository;

public class GradeRepositoryImpl extends AbstractBaseDAO<Grade> implements GradeRepository {

    private static final GradeMapper gradeMapper = new GradeMapper();

    @Override
    public void save(Grade grade) {
        doSave("INSERT INTO grades VALUES (?, ?, ?)",
                statement -> {
                    statement.setString(1, grade.getId());
                    statement.setString(2, grade.getLanguage().getName());
                    statement.setString(3, grade.getLevel().toString());
                });
    }

    @Override
    public void delete(String id) {
        doDeleteByKey("DELETE FROM grades WHERE grade_id = ?", id);
    }

    @Override
    public Grade findByLanguageAndLevel(String language, CEFRLevel level) {
        return doFindBy("SELECT grade_id, language_name, value FROM grades WHERE language_name = ? AND value = ?",
                statement -> {
                    statement.setString(1, language);
                    statement.setString(2, level.toString());
                },
                gradeMapper::toGrade
        );
    }

    @Override
    public Grade findById(String id) {
        return doFindBy("SELECT grade_id, language_name, value FROM grades WHERE grade_id = ?",
                statement -> statement.setString(1, id),
                gradeMapper::toGrade);
    }
}
