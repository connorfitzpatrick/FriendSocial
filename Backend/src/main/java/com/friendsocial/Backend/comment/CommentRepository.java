package com.friendsocial.Backend.comment;

import com.friendsocial.Backend.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  @Query("SELECT p, pr.username, pr.firstName, pr.lastName, pr.profilePic FROM Post p JOIN p.profile pr")
  List<Comment[]> findPost();

  @Query("SELECT p FROM Post p WHERE p.profileId = ?1")
  List<Comment> findCommentsOfPostId(Long id);
}
