package by.poskrobko.repository.impl;

import by.poskrobko.dto.PaymentDTO;
import by.poskrobko.mapper.PaymentMapper;
import by.poskrobko.model.Payment;
import by.poskrobko.repository.PaymentRepository;
import by.poskrobko.repository.UserRepository;
import by.poskrobko.util.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static by.poskrobko.TestData.*;

class PaymentRepositoryImplTest {

    private final PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    @BeforeEach
    void setUp() {
        DBManager.dropDatabase();
        DBManager.initDatabase();
        userRepository.save(USER_3);
        paymentRepository.save(PAYMENT_1_USER_3);
        paymentRepository.save(PAYMENT_2_USER_3);
    }

    @Test
    void save() {
        LocalDate date = LocalDate.of(2024, 12, 20);
        Payment payment = new Payment("payment_uuid_3", date, USER_3, 30000, "For something");
        paymentRepository.save(payment);
        Payment actual = paymentRepository.findAllByDate(date).stream().findFirst().orElseThrow();
        Assertions.assertEquals(payment, actual);
    }
}