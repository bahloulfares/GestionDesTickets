package com.csys.template.domain.enums;

public enum Role {
    ROLE_ADMIN("Admin"),
    ROLE_AUTRE("Autre");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Role fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }

        throw new IllegalArgumentException("Invalid role: " + value);
    }
}
