package com.security.demo.controller.post.dto;

import com.security.demo.domain.post.Post;

public class PostDto {

    private Long id;
    private String writer;
    private String title;
    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.writer = post.getApplicationUser().getNickName();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
