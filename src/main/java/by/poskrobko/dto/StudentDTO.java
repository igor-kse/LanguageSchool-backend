package by.poskrobko.dto;

import java.util.List;

public record StudentDTO(
        String id,
        int age,
        String firstName,
        String lastName,
        String email,
        String channel,
        String hobbies,
        String note
) {}
