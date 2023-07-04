package com.friendsocial.Backend.post;

import com.friendsocial.Backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// This will have all of the resources for the API
@RestController
@RequestMapping(path="api/v1/posts")

// API LAYER
// -

public class PostController {
  private final PostService postService;

  @Autowired
  private ProfileRepository profileRepository;


  @Autowired
  public PostController(PostService postService) {
    /*
      This here should be avoided in favor of dependency injection.
      this.postService = new PostService();

      @Autowired above instantiates a profileService and injects it into the constructor.
     */
    this.postService = postService;
  }

  // GET ALL POSTS
  // Get mapping because we want to get something out from our server
  @GetMapping
  public List<Post> getPosts() {
    return postService.getPosts();
  }

  // POST (ADD) A Post
  // @RequestBody because we are taking the profile that comes from the client. Take request and map to profile
  @PostMapping
  public void createPost(@RequestBody Post postRequest) {
    // Save the post entity
    Long profile = postRequest.getProfile().getId();
    postService.addNewPost(postRequest);

  }

  // DELETE A Post
  // pass the postId within the path. Grab the post ID with @PathVariable
  @DeleteMapping(path = "{postId}")
  public void deletePost(@PathVariable("postId") Long id) {
    postService.deletePost(id);
  }
}
