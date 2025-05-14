package by.poskrobko.dto;

import java.time.LocalDate;

public record PaymentDTO(String id, String user, long amount, LocalDate date, String description) {
}
