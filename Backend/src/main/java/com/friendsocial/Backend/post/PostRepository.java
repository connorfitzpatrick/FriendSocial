package com.friendsocial.Backend.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Data Access Layer
//   - Handles data access / database interaction
public interface PostRepository
  extends JpaRepository<Post, Long>
  {
    // Custom function to find profile by email. Transforms to `SELECT * FROM profiles WHERE email = ?`
    @Query("SELECT p, pr.username, pr.firstName, pr.lastName, pr.profilePic FROM Post p JOIN p.profile pr")
    List<Object[]> findPostsAndProfileInfo();

    @Query("SELECT p FROM Post p WHERE p.profileId = ?1")
    List<Post> findPostsOfProfileId(Long id);

  }
