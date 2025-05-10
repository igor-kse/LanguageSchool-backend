package by.poskrobko.mapper;

import by.poskrobko.dto.ScheduleDTO;
import by.poskrobko.model.Group;
import by.poskrobko.model.Schedule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    public ScheduleDTO toScheduleDTO(ResultSet resultSet) {
        try {
            String id = resultSet.getString("schedule_id");
            String groupName = resultSet.getString("name");
            String languageName = resultSet.getString("language_name");
            String teacherFirstName = resultSet.getString("firstname");
            String teacherLastName = resultSet.getString("lastname");
            String languageScaleName = resultSet.getString("language_scale_name");
            String scaleLevelName = resultSet.getString("scale_level_name");
            String dayOfWeek = resultSet.getString("dayOfWeek");
            String startTime = resultSet.getString("startTime");
            String endTime = resultSet.getString("endTime");
            return new ScheduleDTO(id, groupName, languageName,
                    teacherFirstName + " " + teacherLastName,
                    scaleLevelName + " (" + languageScaleName + ")",
                    DayOfWeek.valueOf(dayOfWeek),
                    LocalTime.parse(startTime),
                    LocalTime.parse(endTime)
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ScheduleDTO> toScheduleDTOs(ResultSet resultSet) {
        List<ScheduleDTO> dtoList = new ArrayList<>();
        try {
            while (resultSet.next()) {
               var dto = toScheduleDTO(resultSet);
               dtoList.add(dto);
            }
            return dtoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
