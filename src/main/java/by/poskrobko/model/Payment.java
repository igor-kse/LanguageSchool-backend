package by.poskrobko.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Payment {
    private String id;
    private User user;
    private long amount;
    private LocalDate date;
    private String description;

    public Payment(String id, LocalDate date, User user, long amount, String description) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.amount = amount;
        this.description = description;
    }

    public Payment(LocalDate date, User user, long amount, String description) {
        this.id = UUID.randomUUID().toString();
        this.date = date;
        this.amount = amount;
        this.user = user;
        this.description = description;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;

        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", id=" + id +
                ", user=" + user +
                ", date=" + date +
                '}';
    }
}
