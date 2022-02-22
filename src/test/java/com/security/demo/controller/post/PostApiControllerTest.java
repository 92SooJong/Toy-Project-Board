package com.security.demo.controller.post;


import com.security.demo.controller.post.dto.PostReadResponseDto;
import com.security.demo.controller.post.dto.PostSaveRequestDto;
import com.security.demo.domain.post.Post;
import com.security.demo.domain.post.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.swing.*;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "application-test.yml")
@AutoConfigureJsonTesters
class PostApiControllerTest {

    @Autowired private PostRepository postRepository;
    @Autowired private WebApplicationContext context;
    @LocalServerPort private int port;
    @Autowired private JacksonTester<PostReadResponseDto> postReadResponseDtoJacksonTester;

    private MockMvc mockMvc;

    @BeforeEach
    public void beforeTest(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글깨짐 방지
                .apply(springSecurity())
                .build();


    }



    @Test
    @WithMockUser(roles = "USER") // USER 권한 가진 테스트용 사용자
    @Transactional // TODO - JPA의 LAZY 개념 이해하기 : 없으면 LazyInitializationException - no session 에러 발생함!
    void canGetPost() throws Exception {

        // given
        Post findedPost = postRepository.getById(1L);
        PostReadResponseDto postReadResponseDto = PostReadResponseDto.builder()
                .postId(findedPost.getId())
                .title(findedPost.getTitle())
                .nickname(findedPost.getApplicationUser().getNickName())
                .content(findedPost.getContent())
                .build();

        // when
        String url = "http://localhost:" + port + "/api/v1/post/" + "1";
        MockHttpServletResponse response = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                postReadResponseDtoJacksonTester.write(postReadResponseDto).getJson());


    }

    @Test
    void addPost() throws Exception {
        //given
        // 사용자 하나 가져온다.

        //



        //when

        //then


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