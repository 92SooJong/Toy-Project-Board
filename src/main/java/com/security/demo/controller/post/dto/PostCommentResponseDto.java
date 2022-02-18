package com.security.demo.controller.post.dto;

import com.security.demo.domain.post.Post;
import com.security.demo.domain.post.PostComment;
import com.security.demo.domain.user.ApplicationUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentResponseDto {

    private String commentContent;
    private String nickname;


    @Builder
    public PostCommentResponseDto(String commentContent, String nickname) {
        this.commentContent = commentContent;
        this.nickname = nickname;
    }
}
