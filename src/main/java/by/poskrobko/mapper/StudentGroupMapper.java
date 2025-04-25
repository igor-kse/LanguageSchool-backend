package by.poskrobko.mapper;

import by.poskrobko.repository.impl.StudentGroupDAOImpl;

import java.sql.ResultSet;

public class StudentGroupMapper extends BaseMapper<StudentGroupDAOImpl.StudentGroup> {

    public StudentGroupDAOImpl.StudentGroup toStudentGroup(ResultSet resultSet) {
        return map(resultSet, rs -> {
            String userId = resultSet.getString("user_id");
            String groupId = resultSet.getString("group_id");
            if (userId == null || groupId == null) {
                return null;
            }
            return new StudentGroupDAOImpl.StudentGroup(userId, groupId);
        });
    }
}
