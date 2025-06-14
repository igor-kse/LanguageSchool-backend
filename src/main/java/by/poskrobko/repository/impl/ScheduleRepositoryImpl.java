package by.poskrobko.repository.impl;

import by.poskrobko.dto.ScheduleDTO;
import by.poskrobko.dto.ScheduleToPostDTO;
import by.poskrobko.mapper.ScheduleMapper;
import by.poskrobko.model.Group;
import by.poskrobko.model.Schedule;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.ScheduleRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class ScheduleRepositoryImpl extends AbstractBaseDAO<Schedule> implements ScheduleRepository {

    private final ScheduleMapper scheduleMapper = new ScheduleMapper();

    @Override
    public void save(String id, String groupId, String dayOfWeek, String startTime, String endTime) {
        doSave("INSERT INTO schedule VALUES(?, ?, ?, ?, ?)",
                statement -> {
                    statement.setString(1, id);
                    statement.setString(2, groupId);
                    statement.setString(3, dayOfWeek);
                    statement.setString(4, startTime);
                    statement.setString(5, endTime);
                });
    }

    @Override
    public List<Schedule> findAllByGroup(Group group) {
        return doFindAllBy("SELECT schedule_id, group_id, dayOfWeek, startTime, endTime FROM schedule WHERE group_id = ?",
                statement -> statement.setString(1, group.getId()),
                resultSet -> scheduleMapper.toSchedule(resultSet, group));
    }

    @Override
    public List<ScheduleDTO> findAll() {
        return sqlExecutor.execute(connection -> {
            String sql = """
                    SELECT schedule_id, g.name, g.language_name, u.firstName, u.lastName, s.language_scale_name,
                           s.scale_level_name, sch.dayOfWeek, startTime, endTime
                    FROM schedule sch
                        INNER JOIN groups g ON sch.group_id = g.group_id
                        INNER JOIN users u ON g.teacher_id = u.user_id
                        INNER JOIN scale_levels s ON g.scale_level_id = s.scale_level_id
                    """;
            var statement = connection.prepareStatement(sql);
            var resultSet = statement.executeQuery();
            return scheduleMapper.toScheduleDTOs(resultSet);
        });
    }

    @Override
    public List<ScheduleDTO> findAllByTeacher(String teacherId) {
        return sqlExecutor.execute(connection -> {
            String sql = """
                    SELECT schedule_id, g.name, g.language_name, u.firstName, u.lastName, s.language_scale_name,
                           s.scale_level_name, sch.dayOfWeek, startTime, endTime
                    FROM schedule sch
                        INNER JOIN groups g ON sch.group_id = g.group_id
                        INNER JOIN users u ON g.teacher_id = u.user_id
                        INNER JOIN scale_levels s ON g.scale_level_id = s.scale_level_id
                    WHERE teacher_id = ?
                    """;
            var statement = connection.prepareStatement(sql);
            statement.setString(1, teacherId);
            var resultSet = statement.executeQuery();
            return scheduleMapper.toScheduleDTOs(resultSet);
        });
    }

    @Override
    public List<ScheduleDTO> findAllByStudent(String studentId) {
        return sqlExecutor.execute(connection -> {
            String sql = """
                    SELECT schedule_id, g.name, g.language_name, u.firstName, u.lastName, s.language_scale_name,
                           s.scale_level_name, sch.dayOfWeek, startTime, endTime
                    FROM schedule sch
                             INNER JOIN groups g ON sch.group_id = g.group_id
                             INNER JOIN users u ON g.teacher_id = u.user_id
                             INNER JOIN scale_levels s ON g.scale_level_id = s.scale_level_id
                             INNER JOIN student_group sg on g.group_id = sg.group_id
                    WHERE student_id = ?
                    """;
            var statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);
            var resultSet = statement.executeQuery();
            return scheduleMapper.toScheduleDTOs(resultSet);
        });
    }

    @Override
    public ScheduleDTO findById(String id) {
        return sqlExecutor.execute(connection -> {
            String sql = """
                    SELECT schedule_id, g.name, g.language_name, u.firstName, u.lastName, s.language_scale_name,
                           s.scale_level_name, sch.dayOfWeek, startTime, endTime
                    FROM schedule sch
                        INNER JOIN groups g ON sch.group_id = g.group_id
                        INNER JOIN users u ON g.teacher_id = u.user_id
                        INNER JOIN scale_levels s ON g.scale_level_id = s.scale_level_id
                    WHERE schedule_id = ?
                    """;
            var statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            var resultSet = statement.executeQuery();
            return scheduleMapper.toScheduleDTO(resultSet);
        });
    }

    @Override
    public void delete(String id) {
        doDeleteByKey("DELETE FROM schedule WHERE schedule_id = ?", id);
    }

    @Override
    public void update(ScheduleToPostDTO schedule) {
        doUpdate("UPDATE schedule SET group_id = ?, dayOfWeek = ?, startTime = ?, endTime = ? WHERE schedule_id = ?",
                statement -> {
                    statement.setString(1, schedule.groupId());
                    statement.setString(2, schedule.dayOfWeek());
                    statement.setString(3, schedule.startTime());
                    statement.setString(4, schedule.endTime());
                    statement.setString(5, schedule.id());
                });
    }
}
