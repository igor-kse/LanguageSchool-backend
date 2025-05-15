package by.poskrobko.service;

import by.poskrobko.dto.PaymentDTO;
import by.poskrobko.model.Payment;
import by.poskrobko.model.User;
import by.poskrobko.repository.PaymentRepository;
import by.poskrobko.repository.UserRepository;
import by.poskrobko.repository.impl.PaymentRepositoryImpl;
import by.poskrobko.repository.impl.UserRepositoryImpl;
import exception.NotExistingEntityException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PaymentService {
    private final PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public String add(String userId, long amount, LocalDate date, String description) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(date);
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NotExistingEntityException("User " + userId + " not found");
        }
        Payment payment = new Payment(date, user, amount, description);
        paymentRepository.save(payment);
        return payment.getId();
    }

    public void update(PaymentDTO dto) {
        Objects.requireNonNull(dto);
        Objects.requireNonNull(dto.id());
        if (dto.amount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        paymentRepository.update(dto.id(), dto.user(), dto.amount(), dto.date(), dto.description());
    }

    public void delete(String id) {
        Objects.requireNonNull(id);
        paymentRepository.delete(id);
    }

    public List<PaymentDTO> getAll() {
        return paymentRepository.findAll();
    }

    public PaymentDTO getById(String id) {
        return paymentRepository.findById(id);
    }

    public List<PaymentDTO> getAllByTeacherStudents(String teacherId) {
        Objects.requireNonNull(teacherId);
        return paymentRepository.findAllByTeacherStudents(teacherId);
    }

    public List<PaymentDTO> getPaymentsByUser(String studentId) {
        Objects.requireNonNull(studentId, "studentId cannot be null");
        return paymentRepository.findAllByStudent(studentId);
    }
}
