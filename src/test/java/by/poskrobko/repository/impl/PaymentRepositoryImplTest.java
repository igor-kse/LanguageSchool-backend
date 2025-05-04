package by.poskrobko.repository.impl;

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

    @Test
    void findAllByUser() {
        List<Payment> actual = paymentRepository.findAllByUser(USER_3);
        List<Payment> expected = List.of(PAYMENT_1_USER_3, PAYMENT_2_USER_3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void update() {
        Payment expected = paymentRepository.findAllByDate(PAYMENT_1_USER_3_DATE_1).stream().findFirst().orElseThrow();
        LocalDate newDate = PAYMENT_1_USER_3_DATE_1.plusDays(10);
        expected.setDate(newDate);
        expected.setAmount(expected.getAmount() * 3);
        expected.setDescription("The new Description");
        expected.setUser(USER_4);
        paymentRepository.update(expected);
        Payment actual = paymentRepository.findAllByDate(newDate).stream().findFirst().orElseThrow();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void delete() {
        paymentRepository.delete(PAYMENT_UUID_1);
        paymentRepository.delete(PAYMENT_UUID_2);
        Assertions.assertIterableEquals(paymentRepository.findAllByUser(USER_3), List.of());
    }
}