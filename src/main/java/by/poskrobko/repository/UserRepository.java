package by.poskrobko.repository;

import by.poskrobko.model.User;

import java.util.List;

public interface UserRepository {
    void save(User user);

    User findById(String id);

    List<User> findAll();

    void update(User user);

    void delete(String id);

    User findByEmail(String email);
}
