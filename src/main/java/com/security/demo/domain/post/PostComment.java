package com.security.demo.domain.post;

import com.security.demo.domain.BaseTimeEntity;
import com.security.demo.domain.user.ApplicationUser;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor
public class PostComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_user_id")
    private ApplicationUser applicationUser;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public PostComment(Long id, ApplicationUser applicationUser, Post post) {
        this.id = id;
        this.applicationUser = applicationUser;
        this.post = post;
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

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
