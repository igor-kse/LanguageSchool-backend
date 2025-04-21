package by.poskrobko.repository.impl;

import by.poskrobko.mapper.RoleMapper;
import by.poskrobko.model.Role;
import by.poskrobko.repository.AbstractBaseDAO;
import by.poskrobko.repository.UserRoleDao;
import by.poskrobko.util.SQLMappable;
import by.poskrobko.util.Settable;

import java.util.HashSet;
import java.util.Set;

public class UserRoleDaoImpl extends AbstractBaseDAO<Role> implements UserRoleDao {

    private final RoleMapper roleMapper = new RoleMapper();

    @Override
    public void save(String userId, Role role) {
        Set<Role> roles = findByUserId(userId);
        if (!roles.contains(role)) {
            doSave("INSERT INTO user_roles(user_id, role) VALUES (?, ?)",
                    statement -> {
                        statement.setString(1, userId);
                        statement.setString(2, role.toString());
                    });
        }
    }

    @Override
    public void addRoles(String userId, Set<Role> roles) {
        Set<Role> existingRoles = findByUserId(userId);
        for (Role role : roles) {
            if (!existingRoles.contains(role)) {
                doSave("INSERT INTO user_roles(user_id, role) VALUES (?, ?)",
                        statement -> {
                            statement.setString(1, userId);
                            statement.setString(2, role.toString());
                        });
            }
        }
    }

    @Override
    public Set<Role> findByUserId(String id) {
        Settable settable = statement -> statement.setString(1, id);
        return new HashSet<>(doFindAllBy("SELECT role FROM user_roles WHERE user_id = ?",
                settable, roleMapper::toRole));
    }

    @Override
    public Set<Role> findByUserEmail(String email) {
        Settable settable = statement -> statement.setString(1, email);
        SQLMappable<Role> mappable = resultSet -> Role.valueOf(resultSet.getString("role"));
        return new HashSet<>(
                doFindAllBy("SELECT role FROM user_roles INNER JOIN users ON user_roles.user_id = users.user_id " +
                        "WHERE email = ?", settable, mappable)
        );
    }

    @Override
    public void deleteByUserId(String id) {
        doDeleteByKey("DELETE FROM user_roles WHERE user_id = ?", id);
    }

    @Override
    public void deleteByUserIdAndRole(String userId, Role role) {
        doDeleteBySettable("DELETE FROM user_roles WHERE user_id = ? AND role = ?",
                statement -> {
                    statement.setString(1, userId);
                    statement.setString(2, role.toString());
                });
    }
}
