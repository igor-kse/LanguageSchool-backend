package by.poskrobko.repository.impl;

import by.poskrobko.mapper.StudentGroupMapper;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.StudentGroupDAO;
import by.poskrobko.util.Settable;

import java.util.List;

public class StudentGroupDAOImpl extends AbstractBaseDAO<StudentGroupDAOImpl.StudentGroup> implements StudentGroupDAO {

    private final StudentGroupMapper studentGroupMapper = new StudentGroupMapper();

    @Override
    public void save(StudentGroup studentGroup) {
        Settable settable = statement -> {
            statement.setString(1, studentGroup.userId);
            statement.setString(2, studentGroup.groupId);
        };
        doSave("INSERT INTO student_group(student_id, group_id) VALUES (?, ?)", settable);
    }

    @Override
    public List<StudentGroup> findAllByStudentId(String id) {
        return doFindAllBy("SELECT student_id, group_id FROM student_group WHERE student_id = ?",
                statement -> statement.setString(1, id), studentGroupMapper::toStudentGroup);
    }

    @Override
    public List<StudentGroup> findAllByGroupId(String groupId) {
        return doFindAllBy("SELECT student_id, group_id FROM student_group WHERE group_id = ?",
                statement -> statement.setString(1, groupId), studentGroupMapper::toStudentGroup);
    }

    @Override
    public void deleteByStudentId(String id) {
        doDeleteBySettable("DELETE FROM student_group WHERE student_id = ?",
                statement -> statement.setString(1, id));
    }

    @Override
    public void deleteByGroupId(String id) {
        doDeleteBySettable("DELETE FROM student_group WHERE group_id = ?",
                statement -> statement.setString(1, id));
    }

    @Override
    public void deleteByStudentAndGroupId(String userId, String groupId) {
        doDeleteBySettable("DELETE FROM student_group WHERE group_id = ? AND student_id = ?",
                statement -> {
                    statement.setString(1, groupId);
                    statement.setString(2, userId);
                });
    }

    public record StudentGroup(String userId, String groupId) {
    }
}
