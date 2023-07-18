package com.friendsocial.Backend.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  @Query("SELECT c, pr.username, pr.id, po.id FROM Comment c JOIN c.user pr JOIN c.post po")
  List<Comment> findCommentsAndPostUserInfo();

  @Query("SELECT c as comment, pr.username as username, pr.userPic as userPic, pr.id as userId, po.id as postId FROM Comment c JOIN c.user pr JOIN c.post po WHERE c.postId = ?1")
  List<Object[]> findCommentsOfPostId(Long id);
}
