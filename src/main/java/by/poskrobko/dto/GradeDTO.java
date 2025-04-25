package by.poskrobko.dto;

import by.poskrobko.model.CEFRLevel;

public record GradeDTO(String id, LanguageEntryDTO language, CEFRLevel level) {
}
