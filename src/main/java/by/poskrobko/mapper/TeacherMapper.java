package by.poskrobko.mapper;

import by.poskrobko.dto.TeacherDTO;
import by.poskrobko.model.Teacher;
import by.poskrobko.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TeacherMapper extends BaseMapper<Teacher> {

    private static final UserMapper userMapper = new UserMapper();

    private Map<String, Teacher> mapMultiple(ResultSet resultSet) {
        Map<String, Teacher> teachers = new HashMap<>();

        try {
            while (resultSet.next()) {
                String id = resultSet.getString("user_id");
                if (id == null) {
                    return null;
                }
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String education = resultSet.getString("education");
                String language = resultSet.getString("language_name") == null
                        ? ""
                        : resultSet.getString("language_name");

                Teacher teacher = teachers.get(id);
                if (teacher == null) {
                    HashSet<String> languages = new HashSet<>();
                    languages.add(language);
                    var user = new User(id, firstName, lastName, email, "", Collections.emptySet(), null);
                    teachers.put(id, new Teacher(user, education, languages));
                } else {
                    teacher.getLanguages().add(language);
                }
            }
            return teachers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Teacher toTeacher(ResultSet resultSet) {
        Map<String, Teacher> teachers = mapMultiple(resultSet);
        if (teachers == null) {
            return null;
        }
        if (teachers.size() > 1) {
            throw new RuntimeException("Mapping error. More than one teacher was found");
        }
        return !teachers.isEmpty()
                ? teachers.values().iterator().next()
                : null;
    }

    public List<Teacher> toTeachers(ResultSet resultSet) {
        Map<String, Teacher> teachers = mapMultiple(resultSet);
        if (teachers == null) {
            return null;
        }
        return teachers.values().stream().toList();
    }

    public Teacher toTeacher(TeacherDTO dto) {
        var user = new User(dto.id(), dto.firstName(), dto.lastName(), dto.email(), "", Set.of(), null);
        return new Teacher(user, dto.education(), dto.languages());
    }

    public TeacherDTO toTeacherDTO(Teacher teacher) {
        return new TeacherDTO(
                teacher.getId(),
                teacher.getUser().getFirstName(),
                teacher.getUser().getLastName(),
                teacher.getEducation(),
                teacher.getLanguages(),
                teacher.getUser().getEmail());
    }

    public List<TeacherDTO> toTeachersDTO(List<Teacher> teachers) {
        var teacherDTOs = new ArrayList<TeacherDTO>();
        for (Teacher teacher : teachers) {
            teacherDTOs.add(toTeacherDTO(teacher));
        }
        return teacherDTOs;
    }
}
