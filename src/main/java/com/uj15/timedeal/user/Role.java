package com.uj15.timedeal.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Role {

    USER("USER"),
    ADMIN("ADMIN");

    private final String type;

    Role(String type) {
        this.type = type;
    }

    @JsonCreator
    public static Role get(String type) {
        return Arrays.stream(values())
                .filter(v -> v.getType().equals(type))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getType() {
        return this.type;
    }
}
