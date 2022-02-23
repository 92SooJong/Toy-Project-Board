package com.security.demo.controller.post;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.security.demo.controller.post.dto.PostCommentSaveRequestDto;
import com.security.demo.controller.post.dto.PostSaveRequestDto;
import com.security.demo.domain.post.Post;
import com.security.demo.domain.post.PostComment;
import com.security.demo.domain.post.PostCommentRepository;
import com.security.demo.domain.post.PostRepository;
import com.security.demo.domain.user.ApplicationUser;
import com.security.demo.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;



import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PostApiControllerTest {

    @Autowired private PostRepository postRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PostCommentRepository postCommentRepository;
    @Autowired private WebApplicationContext context;

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

    @AfterEach
    public void cleanUp(){
        // 지우는 순서도 중요하다
        postCommentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    @WithMockUser(username = "testUsername") // USER 권한 가진 테스트용 사용자
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
    @WithMockUser(username = "testUsername") // USER 권한 가진 테스트용 사용자
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
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(om.writeValueAsString(postSaveRequestDto)));

        //then
        List<Post> allPost = postRepository.findAll();
        assertThat(allPost.get(0).getTitle()).isEqualTo("testTitle");
        assertThat(allPost.get(0).getContent()).isEqualTo("testContent");


    }

    @Test
    @WithMockUser(username = "testUsername") // testUsername으로 검색할 수 있도록하기
    void deletePost() throws Exception {

        Post savedPost = postRepository.save(Post.builder()
                .title("testTitle")
                .content("testContent")
                .applicationUser(userRepository.findByUsername("testUsername"))
                .build());

        String url = "http://localhost:" + port + "/api/v1/post/" + savedPost.getId();


        mockMvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(0);


    }

    @Test
    @WithMockUser(username = "testUsername")
    void addPostComment() throws Exception {

        //given
        Post savedPost = postRepository.save(Post.builder()
                .title("testTitle")
                .content("testContent")
                .applicationUser(userRepository.findByUsername("testUsername"))
                .build());

        PostCommentSaveRequestDto postCommentSaveRequestDto = new PostCommentSaveRequestDto();
        postCommentSaveRequestDto.setPostId(savedPost.getId());
        postCommentSaveRequestDto.setCommentContent("testCommentContent");

        // when
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        String url = "http://localhost:" + port + "/api/v1/post-comment";
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(om.writeValueAsString(postCommentSaveRequestDto)));

        //then
        List<PostComment> postCommentsByPostId = postCommentRepository.getPostCommentsByPostId(savedPost.getId());
        assertThat(postCommentsByPostId.get(0).getCommentContent())
                .isEqualTo(postCommentSaveRequestDto.getCommentContent());
        assertThat(postCommentsByPostId.get(0).getPost().getId()).isEqualTo(postCommentSaveRequestDto.getPostId());

    }

    @Test
    @WithMockUser(username = "testUsername")
    void getPostComments() throws Exception {
        //given
        // 게시글 1건
        Post savedPost = postRepository.save(Post.builder()
                .title("testTitle")
                .content("testContent")
                .applicationUser(userRepository.findByUsername("testUsername"))
                .build());

        // 댓글은 2건 이상 반환될수 있도록 하기
        PostComment postComment = PostComment.builder()
                .post(savedPost)
                .applicationUser(userRepository.findByUsername("testUsername"))
                .commentContent("testComment")
                .build();
        postCommentRepository.save(postComment);

        PostComment postComment2 = PostComment.builder()
                .post(savedPost)
                .applicationUser(userRepository.findByUsername("testUsername"))
                .commentContent("testComment22")
                .build();
        postCommentRepository.save(postComment2);


        // when
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        String url = "http://localhost:" + port + "/api/v1/post-comment/" + savedPost.getId();

        //then 댓글 2건중 첫번째건 검증
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[%s].commentContent",0).value("testComment"))
                .andExpect(jsonPath("$[%s].nickname",0).value("testNickname"));




    }
}