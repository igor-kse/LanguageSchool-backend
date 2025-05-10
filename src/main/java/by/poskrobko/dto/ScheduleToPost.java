package by.poskrobko.dto;

public record ScheduleToPost(
        String id,
        String groupId,
        String dayOfWeek,
        String startTime,
        String endTime
        ) {}
