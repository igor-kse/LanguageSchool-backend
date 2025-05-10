package by.poskrobko.repository.impl;

import by.poskrobko.mapper.ScaleMapper;
import by.poskrobko.model.Scale;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.LanguageScalesRepository;

import java.util.List;

public class LanguageScalesRepositoryImpl extends AbstractBaseDAO<Scale> implements LanguageScalesRepository {

    private final ScaleMapper scaleMapper = new ScaleMapper();

    @Override
    public Scale findByName(String name) {
        return doFindBy("SELECT ls.language_scale_name, language_scale_description, scale_level_id, scale_level_name " +
                        "FROM language_scales ls INNER JOIN scale_levels sl ON ls.language_scale_name = sl.language_scale_name " +
                        "WHERE ls.language_scale_name = ?;",
                statement -> statement.setString(1, name),
                scaleMapper::toScale);
    }

    @Override
    public void save(Scale scale) {
        doSave("INSERT INTO language_scales VALUES (?, ?);",
                statement -> {
                    statement.setString(1, scale.getName());
                    statement.setString(2, scale.getDescription());
                });

        scale.getLevels().forEach(level -> {
            doSave("INSERT INTO scale_levels VALUES(?, ?, ?);",
                    statement -> {
                        statement.setString(1, level.getId());
                        statement.setString(2, level.getName());
                        statement.setString(3, scale.getName());
                    });
        });
    }

    @Override
    public void update(String oldName, Scale scale) {
        delete(oldName);
        save(scale);
    }

    @Override
    public void delete(String name) {
        doDeleteByKey("DELETE FROM language_scales WHERE language_scale_name = ?;", name);
        doDeleteBySettable("DELETE FROM scale_levels WHERE language_scale_name = ?;",
                statement -> statement.setString(1, name));
    }

    @Override
    public List<Scale> findAll() {
        return doFindAll("SELECT ls.language_scale_name, language_scale_description, scale_level_id, scale_level_name " +
                        "FROM language_scales ls INNER JOIN scale_levels sl ON ls.language_scale_name = sl.language_scale_name;",
                scaleMapper::toScales);
    }
}
