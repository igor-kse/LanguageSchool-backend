package by.poskrobko.dto;

import java.util.Set;

public record TeacherDTO(
        String id,
        String firstName,
        String lastName,
        String education,
        Set<String> languages,
        String email) {
}
