package com.security.demo.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserLoginControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void 로그인_화면이_출력된다(){
        String body1 = this.testRestTemplate.getForObject("/", String.class);
        assertThat(body1).contains("로그인하고 시작하기");

        String body2 = this.testRestTemplate.getForObject("/login", String.class);
        assertThat(body2).contains("로그인하고 시작하기");

    }
}