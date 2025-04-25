package by.poskrobko.mapper;

import by.poskrobko.dto.StudentDTO;
import by.poskrobko.model.Student;
import by.poskrobko.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StudentMapper extends BaseMapper<Student> {

    private Map<String, Student> mapMultiple(ResultSet resultSet) {
        Map<String, Student> students = new HashMap<>();

        try {
            while (resultSet.next()) {
                String id = resultSet.getString("student_id");
                if (id == null) {
                    return null;
                }
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                int age = resultSet.getInt("age");
                String channel = resultSet.getString("channel");
                String hobbies = resultSet.getString("hobbies");
                String note = resultSet.getString("note");

                Student student = students.get(id);
                if (student == null) {
                    User user = new User(id, firstName, lastName, email, "", Collections.emptySet(), null);
                    students.put(id, new Student(age, channel, hobbies, note, user));
                }
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Student toStudent(ResultSet resultSet) {
        Map<String, Student> students = mapMultiple(resultSet);
        if (students == null) {
            return null;
        }
        if (students.size() > 1) {
            throw new RuntimeException("Mapping error. More than one student was found");
        }
        return !students.isEmpty()
                ? students.values().iterator().next()
                : null;
    }

    public Student toStudent(StudentDTO dto, User user) {
        if (dto == null) {
            return null;
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (dto.id() != null && !dto.id().equals(user.getUserId())) {
            throw new IllegalArgumentException("User id must be equal to user id");
        }
        return new Student(dto.age(), dto.channel(), dto.hobbies(), dto.note(), user);
    }

    public List<Student> toStudents(ResultSet resultSet) {
        Map<String, Student> students = mapMultiple(resultSet);
        if (students == null) {
            return null;
        }
        return students.values().stream().toList();
    }

    public StudentDTO toStudentDTO(Student student) {
        return new StudentDTO(
                student.getUser().getUserId(),
                student.getAge(),
                student.getUser().getFirstName(),
                student.getUser().getLastName(),
                student.getUser().getEmail(),
                student.getChannel(),
                student.getHobbies(),
                student.getNote()
        );
    }

    public List<StudentDTO> toStudents(List<Student> students) {
        List<StudentDTO> dtos = new ArrayList<>();
        for (Student student : students) {
            dtos.add(toStudentDTO(student));
        }
        return dtos;
    }
}
