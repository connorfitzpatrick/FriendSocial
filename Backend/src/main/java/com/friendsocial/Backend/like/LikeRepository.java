package com.friendsocial.Backend.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
  @Query("SELECT l as like, pr.handle as handle, pr.userPic as userPic FROM Like l JOIN l.user pr JOIN l.post po WHERE l.postId = ?1")
  List<Object[]> findLikesOfPostId(Long id);

  @Query("SELECT l.id as like FROM Like l WHERE l.userId = ?2 AND l.postId = ?1")
  Long findLikeOfUserOnPost(Long postId, Long userId);
}
