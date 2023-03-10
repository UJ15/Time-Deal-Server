package com.uj15.timedeal.user.controller.dto;

import com.uj15.timedeal.user.Role;
import com.uj15.timedeal.user.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserSelectResponse {

    private final String username;
    private final Role role;
    private final LocalDateTime createdAt;

    public static UserSelectResponse of(User user) {
        return new UserSelectResponse(user.getUsername(), user.getRole(), user.getCreatedAt());
    }
}
