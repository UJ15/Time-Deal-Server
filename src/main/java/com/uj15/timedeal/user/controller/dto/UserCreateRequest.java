package com.uj15.timedeal.user.controller.dto;

import com.uj15.timedeal.user.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@RequiredArgsConstructor
public class UserCreateRequest {

    @NotBlank
    @Length(max = 20, min = 1)
    private final String username;

    @NotBlank
    @Length(min = 6, max = 20)
    private final String password;

    @NotNull
    private final Role role;
}
