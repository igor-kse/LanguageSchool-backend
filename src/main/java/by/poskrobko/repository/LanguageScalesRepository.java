package by.poskrobko.repository;

import by.poskrobko.model.Scale;

import java.util.List;

public interface LanguageScalesRepository {

    Scale findByName(String name);

    void save(Scale scale);

    void update(String oldName, Scale scale);

    void delete(String name);

    List<Scale> findAll();
}
