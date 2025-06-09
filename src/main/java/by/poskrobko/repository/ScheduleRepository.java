package by.poskrobko.repository;

import by.poskrobko.dto.ScheduleDTO;
import by.poskrobko.dto.ScheduleToPostDTO;
import by.poskrobko.model.Group;
import by.poskrobko.model.Schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository {
    void save(String id, String groupId, String dayOfWeek, String startTime, String endTime);

    List<Schedule> findAllByGroup(Group group);

    List<ScheduleDTO> findAllByTeacher(String teacherId);

    List<ScheduleDTO> findAllByStudent(String userId);

    List<ScheduleDTO> findAll();

    ScheduleDTO findById(String id);

    void delete(String id);

    void update(ScheduleToPostDTO schedule);
}
