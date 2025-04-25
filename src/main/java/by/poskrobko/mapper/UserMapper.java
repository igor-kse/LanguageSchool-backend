package by.poskrobko.mapper;

import by.poskrobko.dto.UserDTO;
import by.poskrobko.dto.UserToRegisterDTO;
import by.poskrobko.dto.UserToUpdateDTO;
import by.poskrobko.model.Role;
import by.poskrobko.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class UserMapper extends BaseMapper<User> {

    private Map<String, User> mapMultiple(ResultSet resultSet) {
        Map<String, User> users = new HashMap<>();

        try {
            while (resultSet.next()) {
                String id = resultSet.getString("user_id");
                if (id == null) {
                    return null;
                }
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                byte[] avatar = resultSet.getBytes("avatar");
                String role = resultSet.getString("role");

                User user = users.get(id);
                if (user == null) {
                    HashSet<Role> roles = new HashSet<>();
                    if (role != null) {
                        roles.add(Role.valueOf(role));
                    }
                    users.put(id, new User(id, firstName, lastName, email, password, roles, avatar));
                } else {
                    user.getRoles().add(Role.valueOf(role));
                }
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User toUser(ResultSet resultSet) {
        Map<String, User> users = mapMultiple(resultSet);
        if (users == null) {
            return null;
        }
        if (users.size() > 1) {
            throw new RuntimeException("Mapping error. More than one user was found.");
        }
        return !users.isEmpty()
                ? users.values().iterator().next()
                : null;
    }

    public List<User> toUsers(ResultSet resultSet) {
        Map<String, User> users = mapMultiple(resultSet);
        if (users == null) {
            return null;
        }
        return users.values().stream().toList();
    }

    public User toUser(UserToRegisterDTO userDto) {
        byte[] avatar = null;
        if (userDto.avatarBase64() != null && !userDto.avatarBase64().isEmpty()) {
            avatar = Base64.getDecoder().decode(userDto.avatarBase64());
        }
        Set<String> stringRoles = userDto.roles();
        Set<Role> roles;
        if (stringRoles != null && !stringRoles.isEmpty()) {
            roles = stringRoles.stream()
                    .map(Role::valueOf)
                    .collect(Collectors.toSet());
        } else {
            roles = Set.of(Role.USER);
        }
        return new User(UUID.randomUUID().toString(), userDto.firstName(), userDto.lastName(), userDto.email(), userDto.password(), roles, avatar);
    }

    public User toUser(UserToUpdateDTO userDto) {
        byte[] avatar = null;
        if (userDto.avatarBase64() != null && !userDto.avatarBase64().isEmpty()) {
            avatar = Base64.getDecoder().decode(userDto.avatarBase64());
        }
        var roles = userDto.roles() == null
                ? Set.of(Role.USER)
                : userDto.roles().stream().map(Role::valueOf).collect(Collectors.toSet());
        return new User(userDto.id(), userDto.firstName(), userDto.lastName(), userDto.email(), userDto.password(), roles, avatar);
    }

    public UserDTO toUserDTO(User user) {
        List<Role> roles = new ArrayList<>(user.getRoles());
        String avatarBase64 = user.getAvatar() != null
                ? Base64.getEncoder().encodeToString(user.getAvatar())
                : "";
        return new UserDTO(user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail(), roles, avatarBase64);
    }

    public List<UserDTO> toUserDTO(List<User> users) {
        var usersDto = new ArrayList<UserDTO>();
        for (User user : users) {
            usersDto.add(toUserDTO(user));
        }
        return usersDto;
    }
}
