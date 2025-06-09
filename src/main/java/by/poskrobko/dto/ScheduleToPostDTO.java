package by.poskrobko.dto;

public record ScheduleToPostDTO(
        String id,
        String groupId,
        String dayOfWeek,
        String startTime,
        String endTime) {}
