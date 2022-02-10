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
    public String getPosts(Model model){
        model.addAttribute("posts" , postService.findAllDesc());
        return "/post/postList";
    }

    @GetMapping("/writer")
    public String getPostWriter(Model model){
        model.addAttribute("postSaveRequestDto" , new PostSaveRequestDto());
        return "/post/writingPost";
    }

    @PostMapping
    public String addPost(@ModelAttribute PostSaveRequestDto postSaveRequestDto, Authentication authentication){
        postService.save(postSaveRequestDto, authentication.getName());
        return "redirect:/post/list";
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id,Model model){

        PostReadResponseDto postReadResponseDto = postService.findPostById(id);
        model.addAttribute("postReadResponseDto" , postReadResponseDto);
        return "/post/readingPost";
    }




}
