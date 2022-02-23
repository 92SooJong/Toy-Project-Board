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
public class PostCommentSaveRequestDto {

    private String commentContent;
    private Long postId;




    public PostComment toEntity(Post post,ApplicationUser applicationUser){

        return PostComment.builder()
                .post(post)
                .applicationUser(applicationUser)
                .commentContent(this.commentContent)
                .build();



    }

}
