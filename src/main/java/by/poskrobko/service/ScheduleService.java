package by.poskrobko.service;

import by.poskrobko.dto.ScheduleDTO;
import by.poskrobko.dto.ScheduleToPost;
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

    public ScheduleDTO create(ScheduleToPost dto) {
        Objects.requireNonNull(dto);
        String id = UUID.randomUUID().toString();
        scheduleRepository.save(id, dto);
        return scheduleRepository.findById(id);
    }

    public void update(ScheduleToPost dto) {
        Objects.requireNonNull(dto);
        scheduleRepository.update(dto);
    }

    public void delete(String id) {
        Objects.requireNonNull(id);
        scheduleRepository.delete(id);
    }
}
