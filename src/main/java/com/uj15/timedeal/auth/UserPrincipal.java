package com.uj15.timedeal.auth;

import com.uj15.timedeal.user.Role;
import com.uj15.timedeal.user.entity.User;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPrincipal {

    private final UUID userId;
    private final Role role;

    public static UserPrincipal from(User user) {
        return new UserPrincipal(user.getId(), user.getRole());
    }
}
