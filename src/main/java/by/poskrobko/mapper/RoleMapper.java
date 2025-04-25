package by.poskrobko.mapper;

import by.poskrobko.model.Role;

import java.sql.ResultSet;

public class RoleMapper extends BaseMapper<Role> {

    public Role toRole(ResultSet resultSet) {
        return map(resultSet, rs -> {
            String role = resultSet.getString("role");
            return Role.valueOf(role);
        });
    }
}
