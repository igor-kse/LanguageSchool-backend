package by.poskrobko.util;

import by.poskrobko.model.Role;

import java.util.HashSet;
import java.util.Set;

public class Util {

    private Util() {
    }

    public static void assureStringHasLength(String string, String name) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException(name + " cannot be null or empty");
        }
    }

    public static Set<Role> convertStringRolesToEnum(Set<String> stringRoles) {
        Set<Role> roles = new HashSet<>();
        stringRoles.forEach(role -> roles.add(Role.valueOf(role)));
        return roles;
    }
}
