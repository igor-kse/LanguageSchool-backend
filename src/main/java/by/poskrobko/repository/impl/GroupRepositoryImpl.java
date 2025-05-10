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
    public void save(String groupId, String name, String teacherId, String languageName, String scaleLevelId) {
        doSave("INSERT INTO groups VALUES (?, ?, ?, ?, ?)",
                statement -> {
                    statement.setString(1, groupId);
                    statement.setString(2, name);
                    statement.setString(3, teacherId);
                    statement.setString(4, languageName);
                    statement.setString(5, scaleLevelId);
                });
    }

    @Override
    public Group findById(String id) {
        return doFindBy(
                """
                    SELECT g.group_id, g.name, g.teacher_id, g.language_name, g.scale_level_id,
                       t.education, u.firstname, u.lastname, u.email, u.password,
                       sl.language_scale_name, sl.scale_level_name, sl.scale_level_id,
                       ls.language_scale_description
                    FROM groups g
                        INNER JOIN teachers t ON g.teacher_id = t.teacher_id
                        INNER JOIN users u ON t.teacher_id = u.user_id
                        INNER JOIN scale_levels sl ON g.scale_level_id = sl.scale_level_id
                        INNER JOIN language_scales ls ON sl.language_scale_name = ls.language_scale_name
                    WHERE g.group_id = ?
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
    public void update(String groupId, String name, String teacherId, String languageName, String scaleLevelId) {
        String sql = "UPDATE groups SET name = ?, teacher_id = ?, language_name = ?, scale_level_id = ?" +
                     "WHERE group_id = ?";
        Settable params = statement -> {
            statement.setString(1, name);
            statement.setString(2, teacherId);
            statement.setString(3, languageName);
            statement.setString(4, scaleLevelId);
            statement.setString(5, groupId);
        };
        doUpdate(sql, params);
    }

    @Override
    public void saveStudents(String groupId, List<String> studentIds) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO student_group VALUES\n");
        studentIds.forEach(id -> {
            sb.append("    ('").append(id).append("', '").append(groupId).append("'),").append("\n");
        });
        sb.delete(sb.length() - 2, sb.length());
        sb.append(";");
        String sql = sb.toString();
        System.out.println(sql);
        doSave(sql, statement -> {});
    }

    @Override
    public void delete(String id) {
        doDeleteByKey("DELETE FROM groups WHERE group_id = ?", id);
    }

    public void deleteStudents(String groupId) {
        doDeleteByKey("DELETE FROM student_group WHERE group_id = ?", groupId);
    }

    @Override
    public List<Group> findAll() {
        String sql = """
                SELECT g.group_id, g.name, g.teacher_id, g.language_name, g.scale_level_id,
                       t.education, u.firstname, u.lastname, u.email, u.password,
                       sl.language_scale_name, sl.scale_level_name, sl.scale_level_id,
                       ls.language_scale_description
                    FROM groups g
                        INNER JOIN teachers t ON g.teacher_id = t.teacher_id
                        INNER JOIN users u ON t.teacher_id = u.user_id
                        INNER JOIN scale_levels sl ON g.scale_level_id = sl.scale_level_id
                        INNER JOIN language_scales ls ON sl.language_scale_name = ls.language_scale_name
                """;
        return doFindAll(sql, groupMapper::toGroups);
    }
}
