package com.uj15.timedeal.user.controller.dto;

import com.uj15.timedeal.user.Role;
import com.uj15.timedeal.user.entity.User;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSelectResponse {

    private final String username;
    private final Role role;
    private final LocalDateTime createdAt;

    public static UserSelectResponse from(User user) {
        return new UserSelectResponse(user.getUsername(), user.getRole(), user.getCreatedAt());
    }
}
