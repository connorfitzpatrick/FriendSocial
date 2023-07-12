package com.friendsocial.Backend.comment;

import com.friendsocial.Backend.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  @Query("SELECT c, pr.username, pr.id, po.id FROM Comment c JOIN c.profile pr JOIN c.post po")
  List<Comment> findCommentsAndPostProfileInfo();

  @Query("SELECT c as comment, pr.username as username, pr.id as profileId, po.id as postId FROM Comment c JOIN c.profile pr JOIN c.post po WHERE c.postId = ?1")
  List<Object[]> findCommentsOfPostId(Long id);
}
