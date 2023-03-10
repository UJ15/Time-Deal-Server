package com.uj15.timedeal.user.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uj15.timedeal.auth.SessionConst;
import com.uj15.timedeal.auth.UserPrincipal;
import com.uj15.timedeal.user.Role;
import com.uj15.timedeal.user.controller.dto.UserCreateRequest;
import com.uj15.timedeal.user.entity.User;
import com.uj15.timedeal.user.service.UserService;
import com.uj15.timedeal.util.ControllerSetUp;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(controllers = UserRestController.class)
class UserRestControllerTest extends ControllerSetUp {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    private static final String BASE_URL = "/api/users";

    @Nested
    @DisplayName("createUser ????????? ?????????(?????? ??????)")
    class DescribeCreateUser {

        static class UsernameSourceOutOfRange implements ArgumentsProvider {
            @Override
            public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
                return Stream.of(
                        Arguments.of((Object) null),
                        Arguments.of(""),
                        Arguments.of("\t"),
                        Arguments.of("\n"),
                        Arguments.of("a".repeat(21))
                );
            }
        }

        static class PasswordSourceOutOfRange implements ArgumentsProvider {
            @Override
            public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
                return Stream.of(
                        Arguments.of((Object) null),
                        Arguments.of(""),
                        Arguments.of("\t"),
                        Arguments.of("\n"),
                        Arguments.of("a".repeat(5)),
                        Arguments.of("a".repeat(21))
                );
            }
        }

        @Test
        @DisplayName("1 ~ 20??? ?????? ??????, 6 ~ 20?????? ????????????, ????????? ????????? ????????? ????????????.")
        void itCreateUSer() throws Exception {
            //given
            UserCreateRequest requestDto = new UserCreateRequest("username", "password", Role.USER);
            String requestBody = objectMapper.writeValueAsString(requestDto);

            //when
            ResultActions response = mockMvc.perform(
                    post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
            );

            //then
            response.andExpect(status().isCreated());
            verify(userService).createUser(any());
        }

        @ParameterizedTest
        @ArgumentsSource(UsernameSourceOutOfRange.class)
        @DisplayName("?????? ????????? ?????? ?????? 20??? ???????????? BadRequest ??? ????????????.")
        void itCreateUserReturnBasRequestByUsername(String username) throws Exception {
            //given
            UserCreateRequest requestDto = new UserCreateRequest(username, "password", Role.USER);
            String requestBody = objectMapper.writeValueAsString(requestDto);

            //when
            ResultActions response = mockMvc.perform(
                    post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
            );

            //then
            response.andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @ArgumentsSource(PasswordSourceOutOfRange.class)
        @DisplayName("??????????????? ??????, 6??? ??????, 20??? ???????????? BadRequest??? ????????????.")
        void itCreateUserReturnBasRequestByPassword(String password) throws Exception {
            //given
            UserCreateRequest requestDto = new UserCreateRequest("username", password, Role.USER);
            String requestBody = objectMapper.writeValueAsString(requestDto);

            //when
            ResultActions response = mockMvc.perform(
                    post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
            );

            //then
            response.andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("Role??? null????????? BadRequest??? ????????????.")
        void itCreateUserReturnBasRequestByRole(String password) throws Exception {
            //given
            UserCreateRequest requestDto = new UserCreateRequest("username", password, Role.USER);
            String requestBody = objectMapper.writeValueAsString(requestDto);

            //when
            ResultActions response = mockMvc.perform(
                    post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody)
            );

            //then
            response.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("getUser ????????? ?????????(?????? ??????)")
    class DescribeGetUser {

        @Test
        @DisplayName("????????? ???????????? ?????? ????????? ????????? ????????????.")
        void itReturnOk() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            User user = User.of("username", "password", Role.USER);
            UserPrincipal principal = UserPrincipal.from(user);

            session.setAttribute(SessionConst.KEY.name(), principal);
            when(userService.getUser(any())).thenReturn(user);

            //when
            ResultActions response = mockMvc.perform(
                    get(BASE_URL)
            );

            //then
            response.andExpect(status().isOk());
            verify(userService).getUser(any());
        }
    }

    @Nested
    @DisplayName("deleteUser ????????? ?????????(?????? ??????)")
    class Describe {

        @Test
        @DisplayName("????????? Sevice??? delete???????????? ????????????.")
        void itCallServiceMethod() throws Exception {
            //given
            MockHttpSession session = new MockHttpSession();
            User user = User.of("username", "password", Role.USER);
            UserPrincipal principal = UserPrincipal.from(user);

            session.setAttribute(SessionConst.KEY.name(), principal);

            //when
            ResultActions response = mockMvc.perform(
                    delete(BASE_URL)
            );

            //then
            response.andExpect(status().isOk());
            verify(userService).deleteUser(any());
        }
    }
}
