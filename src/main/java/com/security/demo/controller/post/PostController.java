package com.security.demo.controller.post;

import com.security.demo.controller.post.dto.PostDto;
import com.security.demo.controller.post.dto.PostSaveRequestDto;
import com.security.demo.controller.user.dto.RegistrationForm;
import com.security.demo.service.post.PostService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

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
        model.addAttribute("savePostDto" , new PostSaveRequestDto());
        return "/post/writingPost";
    }


    @PostMapping
    public String addPost(@ModelAttribute PostSaveRequestDto postSaveRequestDto, Authentication authentication){

        System.out.println("authentication = " + authentication.getName());

        postService.save(postSaveRequestDto);
        return "redirect:/post/list";
    }


}
