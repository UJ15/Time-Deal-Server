package com.uj15.timedeal.user.entity;

import com.uj15.timedeal.common.entity.BaseEntity;
import com.uj15.timedeal.user.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    private UUID id;

    @Column(length = 20)
    private String username;

    @Column(length = 20)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    private User(UUID id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static User of(String username, String password, Role role) {
        return User.builder()
                .id(UUID.randomUUID())
                .username(username)
                .password(password)
                .role(role)
                .build();
    }

    public void authenticate(String password) {
        if (!this.password.equals(password)) {
            throw new IllegalArgumentException("invalid password");
        }
    }
}
