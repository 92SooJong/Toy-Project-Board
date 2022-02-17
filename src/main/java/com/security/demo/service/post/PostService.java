package com.security.demo.service.post;

import com.security.demo.controller.post.dto.PostDto;
import com.security.demo.controller.post.dto.PostReadResponseDto;
import com.security.demo.controller.post.dto.PostSaveRequestDto;
import com.security.demo.domain.post.Post;
import com.security.demo.domain.post.PostRepository;
import com.security.demo.domain.user.ApplicationUser;
import com.security.demo.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PostService(PostRepository postRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public List<PostDto> findAllDesc(){
        return postRepository.findAllDesc().stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
    }


    public Long save(PostSaveRequestDto postSaveRequestDto, String username) {

        ApplicationUser user = userRepository.findByUsername(username); // 사용자정보 가져오기
        postSaveRequestDto.setApplicationUser(user); // 사용자 정보 세팅
        return postRepository.save(postSaveRequestDto.toEntity()).getId(); // Post 테이블에 저장
    }

    public PostReadResponseDto findPostById(Long id) {
        Post post = postRepository.findById(id).get();
        return PostReadResponseDto.builder()
                .postId(id)
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getApplicationUser().getNickName())
                .build();
    }

    public Long deletePostById(Long postId, String username){

        Post post = postRepository.findPostsByUserId(postId, username);
        postRepository.delete(post);
        return postId;
    }

}
