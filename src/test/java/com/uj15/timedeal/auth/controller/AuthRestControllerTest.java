package com.uj15.timedeal.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.auth.controller.dto.UserLoginRequest;
import com.uj15.timedeal.auth.service.AuthService;
import com.uj15.timedeal.user.Role;
import com.uj15.timedeal.util.ControllerSetUp;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = AuthRestController.class)
class AuthRestControllerTest extends ControllerSetUp {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    private static final String BASE_URL = "/api/auth";

    @Nested
    @DisplayName("Login 메서드 테스트")
    class DescribeLogin {

        @Test
        @DisplayName("성공")
        void itSuccessLogin() throws Exception {
            //given
            UserLoginRequest requestDto = new UserLoginRequest("username", "password");
            UserPrincipal principal = new UserPrincipal(UUID.randomUUID(), Role.USER);
            String requestBody = objectMapper.writeValueAsString(requestDto);
            when(authService.login(any())).thenReturn(principal);

            //when
            ResultActions response = mockMvc.perform(
                    post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
            );

            //then
            response
                    .andExpect(status().isOk());
            verify(authService).login(any());
        }
    }


}