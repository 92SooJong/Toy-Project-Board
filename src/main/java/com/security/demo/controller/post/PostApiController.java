package com.security.demo.controller.post;

import com.security.demo.controller.post.dto.PostDto;
import com.security.demo.controller.post.dto.PostReadResponseDto;
import com.security.demo.controller.post.dto.PostSaveRequestDto;
import com.security.demo.service.post.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostApiController {

    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/api/v1/posts")
    public List<PostDto> getPosts(){
        return postService.findAllDesc();
    }

    @GetMapping("/api/v1/post/{id}")
    public PostReadResponseDto getPost(@PathVariable Long id){
        return postService.findPostById(id);
    }

    @PostMapping("/api/v1/post")
    public Long addPost(@RequestBody PostSaveRequestDto postSaveRequestDto, Authentication authentication){

        System.out.println("postSaveRequestDto = " + authentication.getName());

        return postService.save(postSaveRequestDto, authentication.getName());
    }






}
