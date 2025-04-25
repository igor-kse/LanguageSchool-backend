package by.poskrobko.dto;

import java.util.Set;

public record UserToRegisterDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        Set<String> roles,
        String avatarBase64){}
