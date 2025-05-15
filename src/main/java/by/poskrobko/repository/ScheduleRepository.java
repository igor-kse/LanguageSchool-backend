package by.poskrobko.repository;

import by.poskrobko.dto.ScheduleDTO;
import by.poskrobko.dto.ScheduleToPost;
import by.poskrobko.model.Group;
import by.poskrobko.model.Schedule;

import java.util.List;

public interface ScheduleRepository {
    void save(String id, ScheduleToPost schedule);

    List<Schedule> findAllByGroup(Group group);

    List<ScheduleDTO> findAllByTeacher(String teacherId);

    List<ScheduleDTO> findAllByStudent(String userId);

    List<ScheduleDTO> findAll();

    ScheduleDTO findById(String id);

    void delete(String id);

    void update(ScheduleToPost schedule);
}
