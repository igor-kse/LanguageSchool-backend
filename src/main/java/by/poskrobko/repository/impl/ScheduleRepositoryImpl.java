package by.poskrobko.repository.impl;

import by.poskrobko.mapper.ScheduleMapper;
import by.poskrobko.model.Group;
import by.poskrobko.model.Schedule;
import by.poskrobko.repository.AbstractBaseDAO;

import java.util.List;

public class ScheduleRepositoryImpl extends AbstractBaseDAO<Schedule> implements by.poskrobko.repository.ScheduleRepository {

    private final ScheduleMapper scheduleMapper = new ScheduleMapper();

    @Override
    public void save(Schedule schedule) {
        doSave("INSERT INTO schedule VALUES(?, ?, ?, ?, ?)",
                statement -> {
                    statement.setString(1, schedule.getId());
                    statement.setString(2, schedule.getGroup().getId());
                    statement.setString(3, schedule.getDayOfWeek().toString());
                    statement.setString(4, schedule.getStartTime().toString());
                    statement.setString(5, schedule.getEndTime().toString());
                });
    }

    @Override
    public List<Schedule> findAllByGroup(Group group) {
        return doFindAllBy("SELECT schedule_id, group_id, dayOfWeek, startTime, endTime FROM schedule WHERE group_id = ?",
                statement -> statement.setString(1, group.getId()),
                resultSet -> scheduleMapper.toSchedule(resultSet, group));
    }

    @Override
    public void delete(String id) {
        doDeleteByKey("DELETE FROM schedule WHERE schedule_id = ?", id);
    }

    @Override
    public void update(Schedule schedule) {
        doUpdate("UPDATE schedule SET group_id = ?, dayOfWeek = ?, startTime = ?, endTime = ? WHERE schedule_id = ?",
                statement -> {
                    statement.setString(1, schedule.getGroup().getId());
                    statement.setString(2, schedule.getDayOfWeek().toString());
                    statement.setString(3, schedule.getStartTime().toString());
                    statement.setString(4, schedule.getEndTime().toString());
                    statement.setString(5, schedule.getId());
                });
    }
}
