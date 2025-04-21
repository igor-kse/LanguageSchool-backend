package by.poskrobko.repository;

import by.poskrobko.model.Payment;
import by.poskrobko.model.User;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository {
    void save(Payment payment);

    List<Payment> findAllByUser(User user);

    List<Payment> findAllByDate(LocalDate localDate);

    void update(Payment payment);

    void delete(String id);
}
