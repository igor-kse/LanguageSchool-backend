package by.poskrobko.repository.impl;

import by.poskrobko.mapper.TeacherMapper;
import by.poskrobko.model.Teacher;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.TeacherRepository;

import java.util.List;

public class TeacherRepositoryImpl extends AbstractBaseDAO<Teacher> implements TeacherRepository {

    private final TeacherMapper teacherMapper = new TeacherMapper();

    @Override
    public void save(Teacher teacher) {
        doSave("INSERT INTO teachers VALUES(?,?);",
                statement -> {
                    statement.setString(1, teacher.getId());
                    statement.setString(2, teacher.getEducation());
                });
    }

    @Override
    public Teacher findById(String id) {
        return doFindBy(
                """
                        SELECT users.user_id, users.firstname, users.lastname, users.email, education, language_name
                        FROM teachers
                            INNER JOIN users ON teachers.teacher_id = users.user_id
                            LEFT JOIN teacher_languages ON teachers.teacher_id = teacher_languages.teacher_id
                        WHERE user_id = ?;
                        """,
                statement -> statement.setString(1, id),
                teacherMapper::toTeacher);
    }

    @Override
    public List<Teacher> findAll() {
        return doFindAll(
                """
                        SELECT users.*, education, language_name
                        FROM teachers
                            INNER JOIN users ON teachers.teacher_id = users.user_id
                            LEFT JOIN teacher_languages ON teachers.teacher_id = teacher_languages.teacher_id;
                        """,
                teacherMapper::toTeachers);
    }

    @Override
    public void update(Teacher teacher) {
        doDeleteBySettable("DELETE FROM teacher_languages WHERE teacher_id = ?",
                settable -> {
                    settable.setString(1, teacher.getId());
                });

        doUpdate("UPDATE teachers SET education = ? WHERE teacher_id = ?;",
                statement -> {
                    statement.setString(1, teacher.getEducation());
                    statement.setString(2, teacher.getId());
                });

        teacher.getLanguages().forEach(language -> {
            doSave("INSERT INTO teacher_languages VALUES(?, ?)",
                    settable -> {
                        settable.setString(1, teacher.getId());
                        settable.setString(2, language);
                    });
        });
    }

    @Override
    public void delete(String id) {
        doDeleteByKey("DELETE FROM teachers WHERE teacher_id = ?;", id);
    }
}
