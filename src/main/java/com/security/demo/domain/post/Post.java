package com.security.demo.domain.post;

import com.security.demo.domain.BaseTimeEntity;
import com.security.demo.domain.user.ApplicationUser;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser applicationUser;

    @NotNull
    private String title;

    private String content;

    @Builder
    public Post(ApplicationUser applicationUser, String title, String content) {
        this.applicationUser = applicationUser;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
