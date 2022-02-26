package com.security.demo.domain.post;

import com.security.demo.domain.BaseTimeEntity;
import com.security.demo.domain.user.ApplicationUser;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.Length;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Table(name = "post_comment")
public class PostComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser applicationUser;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "comment_content")
    private String commentContent;

    @Builder
    public PostComment(ApplicationUser applicationUser, Post post, String commentContent) {
        this.applicationUser = applicationUser;
        this.post = post;
        this.commentContent = commentContent;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Long getId() {
        return id;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public Post getPost() {
        return post;
    }

    public String getCommentContent() {
        return commentContent;
    }
}
