package by.poskrobko.dto;

import by.poskrobko.model.User;

import java.time.LocalDate;

public record PaymentDTO(String id, User userDTO, long amount, LocalDate date, String description) {
}
