package by.poskrobko.repository.impl;

import by.poskrobko.model.Schedule;
import by.poskrobko.repository.GroupRepository;
import by.poskrobko.repository.ScheduleRepository;
import by.poskrobko.repository.TeacherRepository;
import by.poskrobko.util.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static by.poskrobko.TestData.*;

class ScheduleRepositoryTest {

    private final ScheduleRepository scheduleRepository = new ScheduleRepositoryImpl();
    private final GroupRepository groupRepository = new GroupRepositoryImpl();
    private final TeacherRepository teacherRepository = new TeacherRepositoryImpl();

    @BeforeEach
    public void setUp() {
        DBManager.dropDatabase();
        DBManager.initDatabase();
        teacherRepository.save(GROUP_1.getTeacher());
        groupRepository.save(GROUP_1);
        groupRepository.save(GROUP_2);
        scheduleRepository.save(SCHEDULE_1);
        scheduleRepository.save(SCHEDULE_2);
    }

    @Test
    public void save() {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(11, 0);
        Schedule expected = new Schedule("schedule_uuid_3", GROUP_1, DayOfWeek.FRIDAY, startTime, endTime);
        scheduleRepository.save(expected);
        Schedule actual = scheduleRepository.findAllByGroup(GROUP_1)
                .stream()
                .filter(s -> s.getDayOfWeek() == DayOfWeek.FRIDAY)
                .findFirst()
                .orElseThrow();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAllByGroup() {
        List<Schedule> expected = List.of(SCHEDULE_1, SCHEDULE_2);
        List<Schedule> actual = scheduleRepository.findAllByGroup(GROUP_1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        scheduleRepository.delete(SCHEDULE_UUID_1);
        Schedule actual = scheduleRepository.findAllByGroup(GROUP_1).stream().findFirst().orElseThrow();
        Assertions.assertEquals(SCHEDULE_2, actual);
    }

    @Test
    public void update() {
        Schedule expected = scheduleRepository.findAllByGroup(GROUP_1).stream().findFirst().orElseThrow();
        expected.setGroup(GROUP_2);
        expected.setDayOfWeek(DayOfWeek.SUNDAY);
        expected.setStartTime(LocalTime.MIDNIGHT);
        expected.setEndTime(LocalTime.NOON);
        scheduleRepository.update(expected);
        Schedule actual = scheduleRepository.findAllByGroup(GROUP_2).stream().findFirst().orElseThrow();
        Assertions.assertEquals(expected, actual);
    }
}