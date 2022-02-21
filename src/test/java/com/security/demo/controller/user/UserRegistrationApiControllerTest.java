package com.security.demo.controller.user;

import com.security.demo.controller.user.dto.UserSaveRequestDto;
import com.security.demo.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRegistrationApiControllerTest {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @LocalServerPort
    private int port;

    private MockMvc mockMvc;

    @Test
    void processRegistration() {

        String username = "testUsername";
        String nickname = "testNickname";
        String password = "testPassword";

        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .username(username)
                .password(password)
                .nickname(nickname).build();

        String url = "http://localhost:" + port + "/api/v1/user";









    }
}