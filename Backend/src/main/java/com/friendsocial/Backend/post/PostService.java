package com.friendsocial.Backend.post;

// SERVICE LAYER
//   - Handles business logic

import com.friendsocial.Backend.user.User;
import com.friendsocial.Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Service allows Spring to detect beans (instances of objects) automatically.
@Service
public class PostService {
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  @Autowired
  public PostService(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  // Business logic of getting all users. Just get them all.
  public List<Object[]> getPosts() {
    return postRepository.findPostsAndUserInfo();
  }

  // Business logic of getting all friendships that are associated with an user
  public List<Object[]> getPostsOfUserById(Long userId) {
    List<Object[]> postList = postRepository.findPostsOfUserId(userId);
    if (postList.isEmpty()){
      throw new IllegalArgumentException("No friendships found");
    }
    return postList;
  }

  // Business logic of Posting (adding) new user
  public Post addNewPost(Long userId, Post postRequest) {
    System.out.println(postRequest);
    System.out.println("SERVICE");

    Optional<User> userOptional = userRepository.findById(userId);
    // Add to User table
    if (userOptional.isEmpty()) {
      // Handle case when user is not found
      throw new IllegalArgumentException("User not found");
    }

    User user = userOptional.get();
    // Otherwise foreign key will be null
    postRequest.setUserId(userId);
    user.addPost(postRequest); // Associate post with user
    return postRepository.save(postRequest);
  }

  // Business logic of deleting a user. Check if it exists first.
  public void deletePost(Long postId) {
    boolean exists = postRepository.existsById(postId);
    if (!exists) {
      throw new IllegalStateException(
              "User with id " + postId + " does not exist"
      );
    }
    postRepository.deleteById(postId);
  }

}
