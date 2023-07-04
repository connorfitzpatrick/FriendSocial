package com.friendsocial.Backend.post;

// SERVICE LAYER
//   - Handles business logic

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Service allows Spring to detect beans (instances of objects) automatically.
@Service
public class PostService {
  private final PostRepository postRepository;

  @Autowired
  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  // Business logic of getting all profiles. Just get them all.
  public List<Post> getPosts() {
    return postRepository.findAll();
  }

  // Business logic of Posting (adding) new profile. Do not add if email already in use.
//  public void addNewPost(Post post) {
//    // Add to Post table
//    postRepository.save(post);
//  }
}
