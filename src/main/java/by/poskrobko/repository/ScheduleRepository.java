package by.poskrobko.repository;

import by.poskrobko.model.Group;
import by.poskrobko.model.Schedule;

import java.util.List;

public interface ScheduleRepository {
    void save(Schedule schedule);

    List<Schedule> findAllByGroup(Group group);

    void delete(String id);

    void update(Schedule schedule);
}
