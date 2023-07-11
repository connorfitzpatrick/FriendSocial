package com.friendsocial.Backend.comment;

import com.friendsocial.Backend.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  @Query("SELECT c, pr.username, pr.id, po.id FROM Comment c JOIN c.profile pr JOIN c.post po")
  List<Comment> findCommentsAndPostProfileInfo();

  @Query("SELECT c FROM Comment c WHERE c.postId = ?1")
  List<Comment> findCommentsOfPostId(Long id);
}
