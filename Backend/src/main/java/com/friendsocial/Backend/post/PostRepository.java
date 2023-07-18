package com.friendsocial.Backend.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Data Access Layer
//   - Handles data access / database interaction
public interface PostRepository
  extends JpaRepository<Post, Long>
  {
    // Custom function to find user by email. Transforms to `SELECT * FROM users WHERE email = ?`
    @Query("SELECT p, pr.username, pr.firstName, pr.lastName, pr.userPic FROM Post p JOIN p.user pr")
    List<Object[]> findPostsAndUserInfo1();

    @Query("SELECT p, pr.username, pr.firstName, pr.lastName, pr.userPic, (SELECT COUNT(l) FROM Like l WHERE l.postId = p.id) FROM Post p JOIN p.user pr")
    List<Object[]> findPostsAndUserInfo();

    @Query("SELECT p FROM Post p WHERE p.userId = ?1")
    List<Post> findPostsOfUserId(Long id);

  }
