package by.poskrobko.mapper;

import by.poskrobko.dto.GroupDTO;
import by.poskrobko.dto.ScaleDTO;
import by.poskrobko.dto.ScaleLevelDTO;
import by.poskrobko.model.Group;
import by.poskrobko.model.Scale;

import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

public class GroupMapper extends BaseMapper<Group> {
    private final TeacherMapper teacherMapper = new TeacherMapper();
    private final LanguageMapper languageMapper = new LanguageMapper();

    public Group toGroup(ResultSet resultSet) {
        return map(resultSet, rs -> {
            String id = resultSet.getString("group_id");
            String name = resultSet.getString("name");
            var teacher = teacherMapper.toTeacherWithoutLanguages(resultSet);
            var language = languageMapper.toLanguageWithoutNote(resultSet);

            String levelId = resultSet.getString("scale_level_id");
            String levelName = resultSet.getString("scale_level_name");
            var level = new Scale.Level(levelId, levelName);

            String scaleName = resultSet.getString("language_scale_name");
            String scaleDescription = resultSet.getString("language_scale_description");
            var scale = new Scale(scaleName, scaleDescription, Set.of());
            if (id == null) {
                return null;
            }
            return new Group(id, name, language, scale, level, teacher);
        });
    }

    public List<Group> toGroups(ResultSet resultSet) {
        return mapMultiple(resultSet, this::toGroup);
    }

    public GroupDTO toGroupDTO(Group group) {
        var scale = group.getScale();
        var scaleDto = new ScaleDTO(scale.getName(), scale.getDescription(), Set.of());
        var scaleLevel = new ScaleLevelDTO(group.getScaleLevel().getId(), group.getScaleLevel().getName());
        var teacher = teacherMapper.toTeacherDTO(group.getTeacher());
        var language = languageMapper.toLanguageDTO(group.getLanguage(), scaleDto);
        return new GroupDTO(group.getId(), group.getName(), teacher, language, scaleLevel);
    }

    public List<GroupDTO> toGroupDTOs(List<Group> groups) {
        return groups.stream().map(this::toGroupDTO).toList();
    }
}
