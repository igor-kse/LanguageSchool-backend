package by.poskrobko.repository;

import by.poskrobko.dto.PaymentDTO;
import by.poskrobko.model.Payment;
import by.poskrobko.model.User;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository {
    void save(Payment payment);

    List<PaymentDTO> findAll();

    PaymentDTO findById(String id);

    List<PaymentDTO> findAllByStudent(String studentId);

    List<PaymentDTO> findAllByTeacherStudents(String teacherId);

    List<Payment> findAllByDate(LocalDate localDate);

    void update(String paymentId, String userId, long amount, LocalDate date, String description);

    void delete(String id);
}
