package com.uj15.timedeal.user.entity;

import com.uj15.timedeal.common.entity.BaseEntity;
import com.uj15.timedeal.user.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    private UUID id;

    @Column(length = 20)
    private String username;

    @Column(length = 20)
    private String password;

    @Enumerated
    private Role role;

    public static User of(UUID id, String password, String username, Role role) {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}