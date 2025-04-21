package by.poskrobko.repository.impl;

import by.poskrobko.mapper.LanguageScalesMapper;
import by.poskrobko.model.LanguageScale;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.LanguageScalesRepository;

import java.util.List;

public class LanguageScalesRepositoryImpl extends AbstractBaseDAO<LanguageScale> implements LanguageScalesRepository {

    private final LanguageScalesMapper languageScalesMapper = new LanguageScalesMapper();

    @Override
    public LanguageScale findByName(String name) {
        return doFindBy("SELECT ls.language_scale_name, language_scale_description, scale_level_id, scale_level_name " +
                        "FROM language_scales ls INNER JOIN scale_levels sl ON ls.language_scale_name = sl.language_scale_name " +
                        "WHERE ls.language_scale_name = ?;",
                statement -> statement.setString(1, name),
                languageScalesMapper::toLanguageScale);
    }

    @Override
    public void save(LanguageScale languageScale) {
        doSave("INSERT INTO language_scales VALUES (?, ?);",
                statement -> {
                    statement.setString(1, languageScale.getName());
                    statement.setString(2, languageScale.getDescription());
                });

        languageScale.getLevels().forEach(level -> {
            doSave("INSERT INTO scale_levels VALUES(?, ?, ?);",
                    statement -> {
                        statement.setString(1, level.getId());
                        statement.setString(2, level.getName());
                        statement.setString(3, languageScale.getName());
                    });
        });
    }

    @Override
    public void update(LanguageScale languageScale) {
        doDeleteBySettable("DELETE FROM scale_levels WHERE language_scale_name = ?;",
                        statement -> statement.setString(1, languageScale.getName()));

        doUpdate("UPDATE language_scales SET language_scale_description = ? WHERE language_scale_name = ?;",
                statement -> {
                    statement.setString(1, languageScale.getDescription());
                    statement.setString(2, languageScale.getName());
                });

        System.out.println(languageScale.getLevels());
        languageScale.getLevels().forEach(level -> {
            doSave("INSERT INTO scale_levels VALUES(?, ?, ?);",
                    statement -> {
                        statement.setString(1, level.getId());
                        statement.setString(2, level.getName());
                        statement.setString(1, languageScale.getName());
                    });
        });
    }

    // FIXME check delete for scale levels
    @Override
    public void delete(String name) {
        doDeleteByKey("DELETE FROM language_scales WHERE language_scale_name = ?;", name);
        doDeleteBySettable("DELETE FROM scale_levels WHERE language_scale_name = ?;",
                statement -> statement.setString(1, name));
    }

    @Override
    public List<LanguageScale> getAll() {
        return doFindAll("SELECT ls.language_scale_name, language_scale_description, scale_level_id, scale_level_name " +
                        "FROM language_scales ls INNER JOIN scale_levels sl ON ls.language_scale_name = sl.language_scale_name;",
                languageScalesMapper::toLanguageScales);
    }
}
