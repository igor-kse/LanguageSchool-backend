package by.poskrobko.repository.impl;

import by.poskrobko.mapper.PaymentMapper;
import by.poskrobko.model.Payment;
import by.poskrobko.model.User;
import by.poskrobko.repository.AbstractBaseDAO;

import java.time.LocalDate;
import java.util.List;

public class PaymentRepositoryImpl extends AbstractBaseDAO<Payment> implements by.poskrobko.repository.PaymentRepository {

    private final PaymentMapper paymentMapper = new PaymentMapper();

    @Override
    public void save(Payment payment) {
        doSave("INSERT INTO payments VALUES (?, ?, ?, ?, ?)",
                statement -> {
                    statement.setString(1, payment.getId());
                    statement.setString(2, payment.getUser().getUserId());
                    statement.setLong(3, payment.getAmount());
                    statement.setString(4, payment.getDate().toString());
                    statement.setString(5, payment.getDescription());
                });
    }

    @Override
    public List<Payment> findAllByUser(User user) {
        return doFindAllBy("SELECT payment_id, user_id, amount, date, description FROM payments WHERE user_id = ?",
                statement -> statement.setString(1, user.getUserId()),
                resultSet -> paymentMapper.toPayment(resultSet, user));
    }

    @Override
    public List<Payment> findAllByDate(LocalDate localDate) {
        return doFindAllBy("SELECT payment_id, user_id, amount, date, description FROM payments WHERE date = ?",
                statement -> statement.setString(1, localDate.toString()),
                resultSet -> paymentMapper.toPayment(resultSet, null));
    }


    @Override
    public void update(Payment payment) {
        doUpdate("UPDATE payments SET user_id = ?, amount = ?, date = ?, description = ? WHERE payment_id = ?",
                statement -> {
                    statement.setString(1, payment.getUser().getUserId());
                    statement.setLong(2, payment.getAmount());
                    statement.setString(3, payment.getDate().toString());
                    statement.setString(4, payment.getDescription());
                    statement.setString(5, payment.getId());
                });
    }

    @Override
    public void delete(String id) {
        doDeleteByKey("DELETE FROM payments WHERE payment_id = ?", id);
    }
}
