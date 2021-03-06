package com.security.demo.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select p from Post p order by p.id")
    List<Post> findAllDesc();

    @Query("select p from Post p where p.id = ?1 and p.applicationUser.username = ?2")
    Post findPostsByUserId(Long postId , String username);

}
