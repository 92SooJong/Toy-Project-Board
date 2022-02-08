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

    private String nickname;
    private String title;
    private String content;
    private String postPassword;
    private String userId;
    private String writer;

    public Post toEntity(){

        return null;

//        return Post.builder()
//                .title(this.title)
//                .content(this.content)
//                .build();
    }


}
