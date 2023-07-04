package com.friendsocial.Backend.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// This will have all of the resources for the API
@RestController
@RequestMapping(path="api/v1/posts")

// API LAYER
// -

public class PostController {
  private final PostService postService;

  @Autowired
  public PostController(PostService postService) {
    /*
      This here should be avoided in favor of dependency injection.
      this.profileService = new ProfileService();

      @Autowired above instantiates a profileService and injects it into the constructor.
     */
    this.postService = postService;
  }

  // GET ALL PROFILES
  // Get mapping because we want to get something out from our server
  @GetMapping
  public List<Post> getPosts() {
    return postService.getPosts();
  }
}
