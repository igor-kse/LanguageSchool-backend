package by.poskrobko.mapper;

import by.poskrobko.model.Group;

import java.sql.ResultSet;
import java.util.List;

public class GroupMapper extends BaseMapper<Group> {
    private final GradeMapper gradeMapper = new GradeMapper();
    private final TeacherMapper teacherMapper = new TeacherMapper();

    public Group toGroup(ResultSet resultSet) {
        return map(resultSet, rs -> {
            var teacher = teacherMapper.toTeacher(resultSet);
            var grade = gradeMapper.toGrade(resultSet);
            String id = resultSet.getString("group_id");
            String name = resultSet.getString("name");
            if (id == null) {
                return null;
            }
            return new Group(id, name, grade, teacher);
        });
    }

    public List<Group> toGroups(ResultSet resultSet) {
        return mapMultiple(resultSet, this::toGroup);
    }
}
