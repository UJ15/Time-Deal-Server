package com.uj15.timedeal.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
public class UserLoginRequest {

    @NotBlank
    @Length(max = 20, min = 1)
    private final String username;

    @NotBlank
    @Length(max = 20, min = 6)
    private final String password;
}
