package by.poskrobko.repository.impl;

import by.poskrobko.mapper.LanguageMapper;
import by.poskrobko.model.Language;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.LanguageRepository;

import java.util.List;

public class LanguageRepositoryImpl extends AbstractBaseDAO<Language> implements LanguageRepository {

    private final LanguageMapper mapper = new LanguageMapper();

    @Override
    public void save(Language language) {
        doSave("INSERT INTO languages VALUES(?, ?, ?)",
                statement -> {
                    statement.setString(1, language.getName());
                    statement.setString(2, language.getScaleName());
                    statement.setString(3, language.getNote());
                });
    }

    @Override
    public Language findByName(String languageName) {
        return doFindBy("SELECT language_name, language_scale_name, note FROM languages WHERE language_name = ?",
                statement -> statement.setString(1, languageName),
                mapper::toLanguage);
    }

    @Override
    public List<Language> findAll() {
        return doFindAll("SELECT language_name, note, language_scale_name FROM languages", mapper::toLanguages);
    }

    @Override
    public void delete(String name) {
        doDeleteByKey("DELETE FROM languages WHERE language_name = ?", name);
    }

    @Override
    public void update(Language language) {
        doUpdate("UPDATE languages SET language_scale_name = ?, note = ? WHERE language_name = ?",
                statement -> {
                    statement.setString(1, language.getScaleName());
                    statement.setString(2, language.getNote());
                    statement.setString(3, language.getName());
                });
    }
}
