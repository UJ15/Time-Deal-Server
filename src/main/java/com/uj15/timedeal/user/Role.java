package com.uj15.timedeal.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Role {

    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @JsonCreator
    public static Role get(String role) {
        return Arrays.stream(values())
                .filter(type -> type.getRole().equals(role))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getRole() {
        return this.role;
    }
}
