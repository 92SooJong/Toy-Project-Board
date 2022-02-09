package com.security.demo.controller.post.dto;

import com.security.demo.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSaveRequestDto {

    private String title;
    private String content;
    private String postPassword;
    private String userId;


    public Post toEntity(){

        return Post.builder()
                .title(this.title)
                .content(this.content)
                .postPassword(this.postPassword)
                .build();
    }


}
