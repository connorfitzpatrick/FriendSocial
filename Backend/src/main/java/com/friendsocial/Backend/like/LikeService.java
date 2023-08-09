package com.friendsocial.Backend.like;

import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.post.PostRepository;
import com.friendsocial.Backend.user.User;
import com.friendsocial.Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Level;
import java.util.logging.Logger;


import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
  private final LikeRepository likeRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  @Autowired
  public LikeService(LikeRepository likeRepository,
                     UserRepository userRepository,
                     PostRepository postRepository) {
    this.likeRepository = likeRepository;
    this.userRepository = userRepository;
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
      Logger.getLogger(LikeService.class.getName()).log(Level.WARNING, "No likes found on post with postId: " + postId);
    }
    return likeList;
  }

  public Long getLikeOfUserOnPost(Long postId, Long userId) {
    Long like = likeRepository.findLikeOfUserOnPost(postId, userId);
    System.out.println("WE MADE IT");
    if (like == null) {
      Logger.getLogger(LikeService.class.getName()).log(Level.WARNING, "No like found for userId: " + userId + " and postId: " + postId);
      return -1L;
    }
    return like;
  }

  // Business logic of Posting (adding) new Like
  public void addNewLike(Long userId, Long postId, Like likeRequest) {
    Optional<Post> postOptional = postRepository.findById(postId);
    Optional<User> userOptional = userRepository.findById(userId);
    // Add to like table
    if (postOptional.isEmpty() || userOptional.isEmpty()) {
      // Handle case when user is not found
      throw new IllegalArgumentException("Post or User not found");
    }

    Post post = postOptional.get();
    User user = userOptional.get();;
    // Otherwise foreign key will be null
    likeRequest.setUserId(userId);
    likeRequest.setPostId(postId);
    post.addLike(likeRequest); // Associate comment with post
    user.addLike(likeRequest); // Associate comment with user
    likeRepository.save(likeRequest);
  }

  // Business logic of deleting a like. Check if it exists first.
  public void deleteLike(Long likeId) {
    boolean exists = likeRepository.existsById(likeId);
    if (!exists) {
      throw new IllegalStateException(
              "Like with id " + likeId + " does not exist"
      );
    }
    // Check if user trying to delete is original poster or commenter
    likeRepository.deleteById(likeId);
  }
}
