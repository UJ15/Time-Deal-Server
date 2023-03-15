package com.uj15.timedeal.auth.controller;

import com.uj15.timedeal.auth.SessionConst;
import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.auth.controller.dto.UserLoginRequest;
import com.uj15.timedeal.auth.service.AuthService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthRestController {

    private final AuthService authService;
    private final HttpSession httpSession;

    public AuthRestController(AuthService authService, HttpSession httpSession) {
        this.authService = authService;
        this.httpSession = httpSession;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody @Valid UserLoginRequest request) {
        UserPrincipal user = authService.login(request);
        httpSession.setAttribute(SessionConst.KEY.name(), user);
    }
}
