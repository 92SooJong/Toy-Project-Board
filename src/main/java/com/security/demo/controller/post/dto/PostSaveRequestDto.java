package com.security.demo.controller.post.dto;

import com.security.demo.domain.post.Post;
import com.security.demo.domain.user.ApplicationUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveRequestDto {

    private String title;
    private String content;
    

    @Builder
    public PostSaveRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity(ApplicationUser applicationUSer){

        return Post.builder()
                .title(this.title)
                .content(this.content)
                .applicationUser(applicationUSer)
                .build();
    }


}
