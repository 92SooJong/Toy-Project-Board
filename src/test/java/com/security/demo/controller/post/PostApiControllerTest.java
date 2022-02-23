package com.security.demo.controller.post;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.security.demo.controller.post.dto.PostReadResponseDto;
import com.security.demo.controller.post.dto.PostSaveRequestDto;
import com.security.demo.domain.post.Post;
import com.security.demo.domain.post.PostRepository;
import com.security.demo.domain.user.ApplicationUser;
import com.security.demo.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.swing.*;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureJsonTesters
@ActiveProfiles("test")
class PostApiControllerTest {

    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private WebApplicationContext context;
    @Autowired private PasswordEncoder passwordEncoder;
    @LocalServerPort private int port;

    private MockMvc mockMvc;

    @BeforeEach
    public void beforeTest(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글깨짐 방지
                .apply(springSecurity())
                .build();

        // 사용자 생성
        ApplicationUser applicationUser = ApplicationUser.builder()
                .username("testUsername")
                .password("testPassword")
                .nickName("testNickname")
                .role("USER")
                .build();
        userRepository.save(applicationUser);

    }



    @Test
    @WithMockUser(roles = "USER") // USER 권한 가진 테스트용 사용자
    // @Transactional // TODO - JPA의 LAZY 개념 이해하기 : 없으면 LazyInitializationException - no session 에러 발생함!
    void canGetPost() throws Exception {

        // given
        Post post = Post.builder()
                .title("testTitle")
                .content("testContent")
                .applicationUser(userRepository.findByUsername("testUsername"))
                .build();
        Post savedPost = postRepository.save(post);

        // when
        String url = "http://localhost:" + port + "/api/v1/post/" + savedPost.getId();

        // then
        mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("testTitle")))
                .andExpect(jsonPath("$.content",is("testContent")));

    }

    @Test
    @WithMockUser(roles = "USER") // USER 권한 가진 테스트용 사용자
    void addPost() throws Exception {
        //given
        PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto.builder()
                .title("testTitle")
                .content("testContent")
                .build();

        //when
        String url = "http://localhost:" + port + "/api/v1/post";


        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        ResultActions resultActions = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(om.writeValueAsString(postSaveRequestDto)));

        String result = resultActions.andReturn().getResponse().getContentAsString();
        MockHttpServletRequest request = resultActions.andReturn().getRequest();
        System.out.println("result = " + result);
        System.out.println("request = " + request.getContentAsString());

        //then
        // findedPost = postRepository.getById(1L);
        //assertThat(findedPost.getId()).isEqualTo(username);


    }

    @Test
    void deletePost() {



    }

    @Test
    void addPostComment() {
    }

    @Test
    void getPostComments() {


    }
}