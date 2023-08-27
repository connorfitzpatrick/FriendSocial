package com.friendsocial.Backend.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;

// Data Access Layer
//   - Handles data access / database interaction
public interface PostRepository
  extends JpaRepository<Post, Long>
  {
//    @Query("SELECT p, pr.userPic, pr.handle, pr.firstName, pr.lastName, " +
//            "(SELECT COUNT(l) FROM Like l WHERE l.postId = p.id) " +
//            "FROM Post p JOIN p.user pr " +
//            "ORDER BY p.timestamp DESC")
//    List<Object[]> findPostsAndUserInfo();

    @Query("SELECT p, pr.userPic, pr.handle, pr.firstName, pr.lastName, " +
            "(SELECT COUNT(l) FROM Like l WHERE l.postId = p.id) " +
            "FROM Post p JOIN p.user pr " +
            "ORDER BY p.timestamp DESC")
    List<Object[]> findPostsAndUserInfo(Pageable pageable);

    @Query("SELECT p, pr.userPic, pr.handle, pr.firstName, pr.lastName " +
            "FROM Post p JOIN p.user pr " +
            "WHERE p.userId = ?1 " +
            "ORDER BY p.timestamp DESC")
    List<Object[]> findPostsOfUserId(Long id, Pageable pageable);
  }
