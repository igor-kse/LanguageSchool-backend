package by.poskrobko.repository.impl;

import by.poskrobko.mapper.GroupMapper;
import by.poskrobko.model.Group;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.GroupRepository;
import by.poskrobko.util.Settable;

import java.util.List;

public class GroupRepositoryImpl extends AbstractBaseDAO<Group> implements GroupRepository {

    private final GroupMapper groupMapper = new GroupMapper();

    @Override
    public void save(Group group) {
        doSave("INSERT INTO groups VALUES (?, ?, ?, ?)",
                statement -> {
                    statement.setString(1, group.getId());
                    statement.setString(2, group.getName());
                    statement.setString(3, group.getGrade().getId());
                    statement.setString(4, group.getTeacher().getId());
                });
    }

    @Override
    public Group findById(String id) {
        return doFindBy(
                """
                        SELECT groups.*, languages.*, grades.value, teachers.education, users.user_id, users.firstName, users.lastName, users.email
                            FROM groups
                                INNER JOIN grades ON groups.grade_id = grades.grade_id
                                INNER JOIN languages ON grades.language_name = languages.language_name
                                INNER JOIN teachers ON groups.teacher_id = teachers.teacher_id
                                INNER JOIN users ON teachers.teacher_id = users.user_id
                            WHERE group_id = ?
                        """,
                statement -> statement.setString(1, id),
                groupMapper::toGroup);
    }

    @Override
    public List<Group> findAllByTeacher(String id) {
        return doFindAllBy(
                """
                        SELECT groups.*, languages.*, teachers.education, users.user_id, users.firstName, users.lastName, users.email
                            FROM groups
                                INNER JOIN grades ON groups.grade_id = grades.grade_id
                                INNER JOIN languages ON grades.language_name = languages.language_name
                                INNER JOIN teachers ON groups.teacher_id = teachers.teacher_id
                                INNER JOIN users ON teachers.teacher_id = users.user_id
                        WHERE teacher_id = ?
                        """,
                statement -> statement.setString(1, id),
                groupMapper::toGroup);
    }

    @Override
    public List<Group> findAllByUser(String userId) {
        return doFindAllBy(
                """
                        SELECT groups.*, languages.*, teachers.education, users.user_id, users.firstName, users.lastName, users.email
                                    FROM groups
                                        INNER JOIN grades ON groups.grade_id = grades.grade_id
                                        INNER JOIN languages ON grades.language_name = languages.language_name
                                        INNER JOIN teachers ON groups.teacher_id = teachers.teacher_id
                                        INNER JOIN users ON teachers.teacher_id = users.user_id
                                WHERE users.user_id = ?
                        """,
                statement -> statement.setString(1, userId),
                groupMapper::toGroup);
    }

    @Override
    public void update(Group group) {
        String sql = "UPDATE groups SET name = ?, grade_id = ?, teacher_id = ? WHERE group_id = ?";
        Settable params = statement -> {
            statement.setString(1, group.getName());
            statement.setString(2, group.getGrade().getId());
            statement.setString(3, group.getTeacher().getId());
            statement.setString(4, group.getId());
        };
        doUpdate(sql, params);
    }

    @Override
    public void delete(String id) {
        doDeleteByKey("DELETE FROM groups WHERE group_id = ?", id);
    }

    @Override
    public List<Group> findAll() {
        String sql = """
                SELECT groups.*, users.*, education
                FROM groups
                    INNER JOIN teachers on groups.teacher_id = teachers.teacher_id
                    INNER JOIN users ON teachers.teacher_id = users.user_id
                    INNER JOIN languages ON groups.language_name = languages.language_name
                WHERE group_id = 'group_uuid_1'
                """;
        return doFindAll(sql, groupMapper::toGroups);
    }
}
