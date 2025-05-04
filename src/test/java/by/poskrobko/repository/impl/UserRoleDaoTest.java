package by.poskrobko.repository.impl;

import by.poskrobko.model.Role;
import by.poskrobko.model.User;
import by.poskrobko.repository.UserRepository;
import by.poskrobko.repository.UserRoleDao;
import by.poskrobko.util.DBManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static by.poskrobko.TestData.*;

class UserRoleDaoTest {

    private static final UserRoleDao userRoleDao = new UserRoleDaoImpl();
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
        // USER_1 already have ADMIN role
        userRoleDao.save(USER_UUID_1, Role.TEACHER);
        userRoleDao.save(USER_UUID_1, Role.STUDENT);
        assertRoles(Set.of(Role.ADMIN, Role.TEACHER, Role.STUDENT), userRoleDao.findByUserId(USER_UUID_1));
    }

    @Test
    public void findByUserId() {
        Assertions.assertIterableEquals(Set.of(Role.ADMIN), userRoleDao.findByUserId(USER_UUID_1));
        Assertions.assertIterableEquals(Set.of(Role.TEACHER), userRoleDao.findByUserId(USER_UUID_2));
        Assertions.assertIterableEquals(Set.of(Role.STUDENT), userRoleDao.findByUserId(USER_UUID_3));
    }

    @Test
    public void findByUserEmail() {
        Assertions.assertIterableEquals(Set.of(Role.ADMIN), userRoleDao.findByUserEmail(USER_1.getEmail()));
        Assertions.assertIterableEquals(Set.of(Role.TEACHER), userRoleDao.findByUserEmail(USER_2.getEmail()));
        Assertions.assertIterableEquals(Set.of(Role.STUDENT), userRoleDao.findByUserEmail(USER_3.getEmail()));
    }

    @Test
    public void deleteByUserId() {
        Set<Role> roles = Set.of(Role.ADMIN, Role.TEACHER, Role.STUDENT, Role.USER);
        User user = userRepository.findById(USER_UUID_1);
        user.setRoles(roles);
        userRepository.update(user);
        assertRoles(roles, userRoleDao.findByUserId(USER_UUID_1));

        userRoleDao.deleteByUserId(USER_UUID_1);
        assertRoles(Collections.emptySet(), userRoleDao.findByUserId(USER_UUID_1));
    }

    @Test
    public void deleteByUserIdAndRole() {
        Set<Role> roles = Set.of(Role.ADMIN, Role.TEACHER, Role.STUDENT, Role.USER);
        User user = userRepository.findById(USER_UUID_1);
        user.setRoles(roles);
        userRepository.update(user);
        assertRoles(roles, userRoleDao.findByUserId(USER_UUID_1));

        userRoleDao.deleteByUserIdAndRole(USER_UUID_1, Role.ADMIN);
        assertRoles(Set.of(Role.TEACHER, Role.STUDENT, Role.USER), userRoleDao.findByUserId(USER_UUID_1));

        userRoleDao.deleteByUserIdAndRole(USER_UUID_1, Role.TEACHER);
        assertRoles(Set.of(Role.STUDENT, Role.USER), userRoleDao.findByUserId(USER_UUID_1));

        userRoleDao.deleteByUserIdAndRole(USER_UUID_1, Role.STUDENT);
        assertRoles(Set.of(Role.USER), userRoleDao.findByUserId(USER_UUID_1));
    }

    private static void assertRoles(Set<Role> expected, Set<Role> actual) {
        List<Role> listExpected = new ArrayList<>(expected);
        List<Role> listActual = new ArrayList<>(actual);
        listExpected.sort(Role::compareTo);
        listActual.sort(Role::compareTo);
        Assertions.assertIterableEquals(listExpected, listActual);
    }
}