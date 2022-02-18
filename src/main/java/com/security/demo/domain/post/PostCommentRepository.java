package com.security.demo.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment,Long> {

    @Query("select pc from PostComment pc where pc.post.id = ?1")
    List<PostComment> getPostCommentsByPostId(Long postId);

}
