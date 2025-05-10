package by.poskrobko.repository;

import by.poskrobko.model.Group;

import java.util.List;

public interface GroupRepository {
    void save(String groupId, String name, String teacherId, String languageName, String scaleLevelId);

    void update(String groupId, String name, String teacherId, String languageName, String scaleLevelId);

    void saveStudents(String groupId, List<String> studentIds);

    void delete(String id);

    void deleteStudents(String groupId);

    List<Group> findAll();

    Group findById(String id);

    List<Group> findAllByTeacher(String id);

    List<Group> findAllByUser(String userId);
}
