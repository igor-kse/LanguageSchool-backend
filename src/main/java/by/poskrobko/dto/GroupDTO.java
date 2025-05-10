package by.poskrobko.dto;

public record GroupDTO(
        String id,
        String name,
        TeacherDTO teacher,
        LanguageDTO language,
        ScaleLevelDTO levelDTO
) {}
