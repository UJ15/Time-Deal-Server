package com.uj15.timedeal.user.controller;

import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.common.auth.annotation.Authentication;
import com.uj15.timedeal.user.controller.dto.UserCreateRequest;
import com.uj15.timedeal.user.controller.dto.UserSelectResponse;
import com.uj15.timedeal.user.entity.User;
import com.uj15.timedeal.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Valid UserCreateRequest request) {
        userService.createUser(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserSelectResponse getUser(@Authentication UserPrincipal principal) {
        User user = userService.getUser(principal);

        return UserSelectResponse.of(user);
    }
}
