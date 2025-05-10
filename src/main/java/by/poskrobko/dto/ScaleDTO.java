package by.poskrobko.dto;

import java.util.Set;

public record ScaleDTO(String name, String description, Set<ScaleLevelDTO> levels) {
}
