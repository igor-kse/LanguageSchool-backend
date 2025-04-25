package by.poskrobko.mapper;

import by.poskrobko.model.Group;
import by.poskrobko.model.Schedule;

import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class ScheduleMapper extends BaseMapper<Schedule> {

    public Schedule toSchedule(ResultSet resultSet, Group group) {
        return map(resultSet, rs -> {
            String id = resultSet.getString("schedule_id");
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(resultSet.getString("dayOfWeek"));
            LocalTime startTime = LocalTime.parse(resultSet.getString("startTime"));
            LocalTime endTime = LocalTime.parse(resultSet.getString("endTime"));
            if (id == null) {
                return null;
            }
            return new Schedule(id, group, dayOfWeek, startTime, endTime);
        });
    }
}
