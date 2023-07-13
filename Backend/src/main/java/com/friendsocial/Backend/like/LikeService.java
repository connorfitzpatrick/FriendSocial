package com.friendsocial.Backend.like;

import com.friendsocial.Backend.post.PostRepository;
import com.friendsocial.Backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
  private final LikeRepository likeRepository;
  private final PostRepository postRepository;
  private final ProfileRepository profileRepository;

  @Autowired
  public LikeService(LikeRepository likeRepository,
                     ProfileRepository profileRepository,
                     PostRepository postRepository) {
    this.likeRepository = likeRepository;
    this.profileRepository = profileRepository;
    this.postRepository = postRepository;
  }

  // Business logic of getting all likes. Just get them all.
  public List<Like> getLikes() {
    return likeRepository.findAll();
  }

  // Business logic of getting all likes that are associated with a post
  public List<Object[]> getLikesOfPostById(Long postId) {
    List<Object[]> likeList = likeRepository.findLikesOfPostId(postId);
    if (likeList.isEmpty()){
      throw new IllegalArgumentException("No comments found");
    }
    return likeList;
  }
}
