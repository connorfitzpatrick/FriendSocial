package com.friendsocial.Backend.post;

import com.friendsocial.Backend.friend.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Data Access Layer
//   - Handles data access / database interaction
public interface PostRepository
  extends JpaRepository<Post, Long>
  {
    // Custom function to find profile by email. Transforms to `SELECT * FROM profiles WHERE email = ?`
    //    @Query("SELECT p FROM Profile p WHERE p.email = ?1")
    //    Optional<Post> findPByEmail(String email);
    @Query("SELECT p FROM Post p WHERE p.profileId = ?1")
    List<Post> findPostsOfProfileId(Long id);

  }
