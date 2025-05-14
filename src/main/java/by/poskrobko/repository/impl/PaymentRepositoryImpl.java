package by.poskrobko.repository.impl;

import by.poskrobko.dto.PaymentDTO;
import by.poskrobko.mapper.PaymentMapper;
import by.poskrobko.model.Payment;
import by.poskrobko.model.User;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.PaymentRepository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

public class PaymentRepositoryImpl extends AbstractBaseDAO<Payment> implements PaymentRepository {

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
    public List<PaymentDTO> findAll() {
        return sqlExecutor.execute(connection -> {
            String sql = """
                    SELECT payment_id, date, amount, date, description, u.firstname, u.lastname
                    FROM payments
                        INNER JOIN users u ON payments.user_id = u.user_id
                    """;
            var statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            return paymentMapper.toPaymentDTOs(rs);
        });
    }

    @Override
    public PaymentDTO findById(String id) {
        return sqlExecutor.execute(connection -> {
            String sql = """
                    SELECT payment_id, date, amount, date, description, u.firstname, u.lastname
                    FROM payments
                        INNER JOIN users u ON payments.user_id = u.user_id
                    WHERE payment_id = ?
                    """;
            var statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            return paymentMapper.toPaymentDTO(rs);
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
    public void update(String paymentId, String userId, long amount, LocalDate date, String description) {
        doUpdate("UPDATE payments SET user_id = ?, amount = ?, date = ?, description = ? WHERE payment_id = ?",
                statement -> {
                    statement.setString(1, userId);
                    statement.setLong(2, amount);
                    statement.setString(3, date.toString());
                    statement.setString(4, description);
                    statement.setString(5, paymentId);
                });
    }

    @Override
    public void delete(String id) {
        doDeleteByKey("DELETE FROM payments WHERE payment_id = ?", id);
    }
}
