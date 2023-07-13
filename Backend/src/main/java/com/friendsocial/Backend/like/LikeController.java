package com.friendsocial.Backend.like;

import com.friendsocial.Backend.post.PostRepository;
import com.friendsocial.Backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/likes")
public class LikeController {
  private final LikeService likeService;

  @Autowired
  private ProfileRepository profileRepository;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  public LikeController(LikeService likeService) {
    this.likeService = likeService;
  }

  // GET ALL LIKES
  // Get mapping because we want to get something out from our server
  @GetMapping
  public List<Like> getLikes() {
    return likeService.getLikes();
  }

  @GetMapping(path = "{postId}")
  public List<Object[]> getLikesByPost(@PathVariable("postId") Long id) {
    return likeService.getLikesOfPostById(id);
  }
}
