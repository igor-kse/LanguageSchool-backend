package by.poskrobko.repository.impl;

import by.poskrobko.mapper.StudentMapper;
import by.poskrobko.model.Student;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.StudentRepository;

import java.util.List;

public class StudentRepositoryImpl extends AbstractBaseDAO<Student> implements StudentRepository {
    private StudentMapper mapper = new StudentMapper();

    @Override
    public void delete(String id) {
        doDeleteByKey("DELETE FROM students WHERE student_id = ?", id);
    }

    @Override
    public Student save(Student student) {
        doSave("INSERT INTO students VALUES(?, ?, ?, ?, ?)",
                statement -> {
                    statement.setString(1, student.getUser().getUserId());
                    statement.setInt(2, student.getAge());
                    statement.setString(3, student.getHobbies());
                    statement.setString(4, student.getChannel());
                    statement.setString(5, student.getNote());
                });
        return student;
    }

    @Override
    public void update(Student student) {
        doSave("UPDATE students SET age = ?, hobbies = ?, channel = ?, note = ? WHERE student_id = ?",
                settable -> {
                    settable.setInt(1, student.getAge());
                    settable.setString(2, student.getHobbies());
                    settable.setString(3, student.getChannel());
                    settable.setString(4, student.getNote());
                    settable.setString(5, student.getUser().getUserId());
                });
    }

    @Override
    public List<Student> getAllStudents() {
        return doFindAll("SELECT " +
                             "    st.student_id, age, hobbies, channel, note, " +
                             "    firstname, lastname, email, avatar " +
                             "FROM students st" +
                             "    LEFT JOIN users u ON st.student_id = u.user_id "
                , mapper::toStudents);
    }
}
