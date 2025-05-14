package by.poskrobko.mapper;

import by.poskrobko.dto.PaymentDTO;
import by.poskrobko.model.Payment;
import by.poskrobko.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public PaymentDTO toPaymentDTO(ResultSet resultSet) {
        try {
            String id = resultSet.getString("payment_id");
            long amount = resultSet.getLong("amount");
            String description = resultSet.getString("description");
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            String date = resultSet.getString("date");
            return new PaymentDTO(id, firstName + " " + lastName, amount, LocalDate.parse(date), description);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PaymentDTO> toPaymentDTOs(ResultSet resultSet) {
        try {
            List<PaymentDTO> dtoList = new ArrayList<>();
            while (resultSet.next()) {
                dtoList.add(toPaymentDTO(resultSet));
            }
            return dtoList;
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
