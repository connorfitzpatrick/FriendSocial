package com.friendsocial.Backend.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  /*
  NOTE: Comment c already has user and post ids. No need add all that info in select
   */
  @Query("SELECT c, pr.handle, pr.id, po.id FROM Comment c JOIN c.user pr JOIN c.post po")
  List<Comment> findCommentsAndPostUserInfo();

  @Query("SELECT c as comment, pr.handle as handle, pr.userPic as userPic, pr.id as userId, po.id as postId FROM Comment c JOIN c.user pr JOIN c.post po WHERE c.postId = ?1")
  List<Object[]> findCommentsOfPostId(Long id);

  @Query("SELECT c as comment, pr.handle as handle " +
          "FROM Comment c " +
          "JOIN c.user pr " +
          "WHERE c.postId = ?1 " +
          "ORDER BY c.timestamp DESC " +
          "LIMIT 2")
  List<Object[]> findTop2ByPostIdOrderByTimestampDesc(Long postId);

}
