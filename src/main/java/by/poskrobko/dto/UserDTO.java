package by.poskrobko.dto;

import by.poskrobko.model.Role;

import java.util.List;

public record UserDTO(String id, String firstName, String lastName, String email, List<Role> roles, String avatarBase64) {
}
