package by.poskrobko.repository.impl;

import by.poskrobko.model.Role;
import by.poskrobko.model.User;
import by.poskrobko.repository.UserRepository;
import by.poskrobko.util.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static by.poskrobko.TestData.*;

class UserRepositoryTest {

    private static final UserRepository userRepository = new UserRepositoryImpl();

    @BeforeEach
    public void setUp() {
        DBManager.dropDatabase();
        DBManager.initDatabase();
        userRepository.save(USER_1);
        userRepository.save(USER_2);
        userRepository.save(USER_3);
    }

    @Test
    public void save() {
        userRepository.save(USER_4);
        Assertions.assertEquals(USER_4, userRepository.findById(USER_UUID_4));
        Assertions.assertEquals(USER_4, userRepository.findByEmail(USER_4.getEmail()));
    }

    @Test
    public void findById() {
        Assertions.assertEquals(USER_1, userRepository.findById(USER_UUID_1));
        Assertions.assertEquals(USER_2, userRepository.findById(USER_UUID_2));
        Assertions.assertEquals(USER_3, userRepository.findById(USER_UUID_3));
    }

    @Test
    public void findAll() {
        List<User> users = userRepository.findAll();
        Assertions.assertIterableEquals(List.of(USER_1, USER_2, USER_3), users);
    }

    @Test
    public void update() {
        User user = userRepository.findById(USER_UUID_1);
        user.setFirstName("newFirstName");
        user.setLastName("newLastName");
        user.setEmail("newEmail");
        user.setPassword("newPassword");
        user.setRoles(Set.of(Role.USER, Role.TEACHER, Role.ADMIN));
        userRepository.update(user);
        Assertions.assertEquals(user, userRepository.findById(USER_UUID_1));
    }

    @Test
    public void delete() {
        userRepository.delete(USER_UUID_1);
        Assertions.assertNull(userRepository.findById(USER_UUID_1));
        userRepository.delete(USER_UUID_2);
        Assertions.assertNull(userRepository.findById(USER_UUID_2));
        userRepository.delete(USER_UUID_3);
        Assertions.assertNull(userRepository.findById(USER_UUID_3));
    }

    @Test
    public void findByEmail() {
        Assertions.assertEquals(USER_1, userRepository.findByEmail(USER_1.getEmail()));
        Assertions.assertEquals(USER_2, userRepository.findByEmail(USER_2.getEmail()));
        Assertions.assertEquals(USER_3, userRepository.findByEmail(USER_3.getEmail()));
    }
}