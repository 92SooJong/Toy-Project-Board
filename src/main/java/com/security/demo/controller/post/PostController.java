package com.security.demo.controller.post;

import com.security.demo.controller.post.dto.PostReadResponseDto;
import com.security.demo.controller.post.dto.PostSaveRequestDto;
import com.security.demo.domain.post.Post;
import com.security.demo.service.post.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/list")
    public String getPostListView(){
        return "/post/postList";
    }

    @GetMapping("/writer")
    public String getPostWriter(){
        return "/post/writingPost";
    }






}
