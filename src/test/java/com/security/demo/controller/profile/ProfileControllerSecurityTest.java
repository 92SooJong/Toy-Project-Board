package com.security.demo.controller.profile;

import com.security.demo.domain.user.ApplicationUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerSecurityTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private MockMvc mockMvc;
    @Autowired private WebApplicationContext context;

    @BeforeEach
    public void beforeTest(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글깨짐 방지
                .apply(springSecurity())
                .build();
    }

    @Test
    public void canAccessToProfileWithoutAuthorization(){
        String expected = "db";

        ResponseEntity<String> response = testRestTemplate.getForEntity("/profile", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);


    }



}