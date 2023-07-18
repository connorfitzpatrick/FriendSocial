package com.friendsocial.Backend.like;

import com.friendsocial.Backend.post.PostRepository;
import com.friendsocial.Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/likes")
public class LikeController {
  private final LikeService likeService;

  @Autowired
  private UserRepository userRepository;

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

  @PostMapping(path = "{userId}/{postId}")
  public void createLike(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId, @RequestBody Like likeRequest) {
    likeService.addNewLike(userId, postId, likeRequest);
  }

  @DeleteMapping(path = "{likeId}")
  public void deleteLike(@PathVariable("likeId") Long likeId) {
    likeService.deleteLike(likeId);
  }

}
