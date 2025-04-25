package by.poskrobko.dto;

import by.poskrobko.model.Grade;
import by.poskrobko.model.Language;

public record GroupDTO(String id, String name, Grade grade, Language language) {
}
