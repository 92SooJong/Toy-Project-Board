package com.security.demo.controller.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.demo.controller.user.dto.UserSaveRequestDto;
import com.security.demo.domain.user.ApplicationUser;
import com.security.demo.domain.user.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserRegistrationApiControllerTest {

    @Autowired private UserRepository userRepository;
    @Autowired private WebApplicationContext context;
    @LocalServerPort
    private int port;

    private MockMvc mockMvc;



    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();
    }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    void canRegisterUser() throws Exception {

        String username = "testUsername";
        String nickname = "testNickname";
        String password = "testPassword";

        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .username(username)
                .password(password)
                .nickname(nickname).build();

        String url = "http://localhost:" + port + "/api/v1/user";

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(userSaveRequestDto)))
                .andExpect(status().isOk());

        ApplicationUser byUsername = userRepository.findByUsername(username);
        assertThat(byUsername.getUsername()).isEqualTo(username);


    }
}