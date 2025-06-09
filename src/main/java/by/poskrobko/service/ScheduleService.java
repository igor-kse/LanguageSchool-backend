package by.poskrobko.service;

import by.poskrobko.dto.ScheduleDTO;
import by.poskrobko.dto.ScheduleToPostDTO;
import by.poskrobko.repository.ScheduleRepository;
import by.poskrobko.repository.impl.ScheduleRepositoryImpl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ScheduleService {
    private final ScheduleRepository scheduleRepository = new ScheduleRepositoryImpl();

    public List<ScheduleDTO> findAll() {
        return scheduleRepository.findAll();
    }

    public List<ScheduleDTO> findByTeacher(String teacherId) {
        return scheduleRepository.findAllByTeacher(teacherId);
    }

    public List<ScheduleDTO> findByStudent(String studentId) {
        return scheduleRepository.findAllByStudent(studentId);
    }

    public ScheduleDTO create(ScheduleToPostDTO dto) {
        Objects.requireNonNull(dto);
        String id = UUID.randomUUID().toString();
        scheduleRepository.save(id, dto.groupId(), dto.dayOfWeek(), dto.startTime(), dto.endTime());
        return scheduleRepository.findById(id);
    }

    public void update(ScheduleToPostDTO dto) {
        Objects.requireNonNull(dto);
        scheduleRepository.update(dto);
    }

    public void delete(String id) {
        Objects.requireNonNull(id);
        scheduleRepository.delete(id);
    }
}
