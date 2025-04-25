package by.poskrobko.service;

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

    public void add(String userId, double amount, LocalDate date, String description) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(date);
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NotExistingEntityException("User " + userId + " not found");
        }
        double roundedAmount = Math.round(amount * 100) / 100.0;
        long convertedAmount = Math.round(roundedAmount * 100);
        Payment payment = new Payment(date, user, convertedAmount, description);
        paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByUser(String userId) {
        Objects.requireNonNull(userId, "userId cannot be null");

        User user = userRepository.findById(userId);
        Objects.requireNonNull(user, "User " + userId + " not found");

        paymentRepository.findAllByUser(user);
        return null;
    }
}
