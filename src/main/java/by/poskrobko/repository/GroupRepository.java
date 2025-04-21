package by.poskrobko.repository;

import by.poskrobko.model.Group;

import java.util.List;

public interface GroupRepository {
    void save(Group group);

    void update(Group group);

    void delete(String id);

    List<Group> findAll();

    Group findById(String id);

    List<Group> findAllByTeacher(String id);

    List<Group> findAllByUser(String userId);
}
