package by.poskrobko.repository.impl;

import by.poskrobko.mapper.UserMapper;
import by.poskrobko.model.Role;
import by.poskrobko.model.User;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class UserRepositoryImpl extends AbstractBaseDAO<User> implements UserRepository {

    private final UserRoleDaoImpl userRoleDao = new UserRoleDaoImpl();

    private final UserMapper userMapper = new UserMapper();

    @Override
    public void save(User user) {
        doSave("INSERT INTO users VALUES(?,?,?,?,?, ?)",
                statement -> {
                    statement.setString(1, user.getUserId());
                    statement.setString(2, user.getFirstName());
                    statement.setString(3, user.getLastName());
                    statement.setString(4, user.getEmail());
                    statement.setString(5, user.getPassword());
                    statement.setBytes(6, user.getAvatar());
                });

        user.getRoles().forEach(role -> {
            userRoleDao.save(user.getUserId(), role);
        });
    }

    @Override
    public User findById(String id) {
        System.out.println(id);
        return doFindBy("SELECT users.user_id, firstName, lastName, email, password, avatar, role " +
                            "FROM users LEFT JOIN user_roles ur on users.user_id = ur.user_id " +
                            "WHERE users.user_id = ?",
                statement -> statement.setString(1, id),
                userMapper::toUser);
    }

    @Override
    public List<User> findAll() {
        return doFindAll("SELECT users.user_id, firstName, lastName, email, password, avatar, role " +
                             "FROM users INNER JOIN user_roles ur on users.user_id = ur.user_id",
                userMapper::toUsers);
    }

    @Override
    public void update(User user) {
        userRoleDao.deleteByUserId(user.getUserId());

        doUpdate("UPDATE users SET firstName = ?, lastName = ?, email = ?, password = ?, avatar = ? WHERE user_id = ?",
                statement -> {
                    statement.setString(1, user.getFirstName());
                    statement.setString(2, user.getLastName());
                    statement.setString(3, user.getEmail());
                    statement.setString(4, user.getPassword());
                    statement.setBytes(5, user.getAvatar());
                    statement.setString(6, user.getUserId());
                });

        user.getRoles().forEach(role -> userRoleDao.save(user.getUserId(), role));
    }

    @Override
    public void delete(String id) {
        doDeleteByKey("DELETE FROM users WHERE user_id = ?", id);
    }

    @Override
    public User findByEmail(String email) {
        return doFindBy("SELECT users.user_id, firstName, lastName, email, password, avatar, role " +
                            "FROM users INNER JOIN user_roles ur on users.user_id = ur.user_id " +
                            "WHERE email = ?",
                statement -> statement.setString(1, email),
                userMapper::toUser);
    }
}
