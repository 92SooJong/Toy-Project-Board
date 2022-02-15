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
    private String password;
    private ApplicationUser applicationUser;



    public Post toEntity(PasswordEncoder passwordEncoder){

        return Post.builder()
                .title(this.title)
                .content(this.content)
                .postPassword(passwordEncoder.encode(this.password))
                .applicationUser(this.applicationUser)
                .build();
    }


}
