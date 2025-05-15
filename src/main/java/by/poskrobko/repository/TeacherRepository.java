package by.poskrobko.repository;

import by.poskrobko.dto.ScheduleDTO;
import by.poskrobko.model.Teacher;

import java.util.List;

public interface TeacherRepository {
    void save(Teacher teacher);

    Teacher findById(String id);

    List<Teacher> findAll();

    void update(Teacher teacher);

    void delete(String id);
}
