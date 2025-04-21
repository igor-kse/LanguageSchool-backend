package by.poskrobko.repository;

import by.poskrobko.model.Role;

import java.util.Set;

public interface UserRoleDao {
    void save(String userId, Role role);

    Set<Role> findByUserId(String id);

    Set<Role> findByUserEmail(String email);

    void deleteByUserId(String id);

    void deleteByUserIdAndRole(String userId, Role role);

    void addRoles(String userId, Set<Role> roles);
}
