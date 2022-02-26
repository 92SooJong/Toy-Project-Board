package com.security.demo.service.post;

import com.security.demo.controller.post.dto.*;
import com.security.demo.domain.post.Post;
import com.security.demo.domain.post.PostComment;
import com.security.demo.domain.post.PostCommentRepository;
import com.security.demo.domain.post.PostRepository;
import com.security.demo.domain.user.ApplicationUser;
import com.security.demo.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostCommentRepository postCommentRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.postCommentRepository = postCommentRepository;
    }

    @Transactional
    public List<PostDto> findAllDesc(){
        return postRepository.findAllDesc().stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }


    public Long save(PostSaveRequestDto postSaveRequestDto, String username) {

        ApplicationUser user = userRepository.findByUsername(username); // 사용자정보 가져오기
        return postRepository.save(postSaveRequestDto.toEntity(user)).getId(); // Post 테이블에 저장
    }

    public PostReadResponseDto findPostById(Long id) {
        Post post = postRepository.findById(id).get();
        return PostReadResponseDto.builder()
                .postId(id)
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getApplicationUser().getNickname())
                .build();
    }

    public Long deletePostById(Long postId, String username){

        Post post = postRepository.findPostsByUserId(postId, username);
        postRepository.delete(post);
        return postId;
    }


    public Long savePostComment(PostCommentSaveRequestDto postCommentSaveRequestDto, String name) {

        ApplicationUser byUsername = userRepository.findByUsername(name);
        Post byId = postRepository.getById(postCommentSaveRequestDto.getPostId());
        return postCommentRepository.save(postCommentSaveRequestDto.toEntity(byId,byUsername)).getId();

    }

    public List<PostCommentResponseDto> getPostComments(Long postId) {

        List<PostComment> postCommentsByPostId = postCommentRepository.getPostCommentsByPostId(postId);
        return postCommentsByPostId.stream().map(postComment -> PostCommentResponseDto.builder()
                .commentContent(postComment.getCommentContent())
                .nickname(postComment.getApplicationUser().getNickname())
                .build())
                .collect(Collectors.toList());

    }
}
