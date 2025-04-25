package by.poskrobko.mapper;

import by.poskrobko.model.Payment;
import by.poskrobko.model.User;

import java.sql.ResultSet;
import java.time.LocalDate;

public class PaymentMapper extends BaseMapper<Payment> {
    public Payment toPayment(ResultSet resultSet, User user) {
        return map(resultSet, rs -> {
            String id = resultSet.getString("payment_id");
            long amount = resultSet.getLong("amount");
            String description = resultSet.getString("description");
            LocalDate localDate = LocalDate.parse(resultSet.getString("date"));
            if (id == null) {
                return null;
            }
            return new Payment(id, localDate, user, amount, description);
        });
    }
}
