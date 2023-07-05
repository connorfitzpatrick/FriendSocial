package com.friendsocial.Backend.post;

import com.friendsocial.Backend.friend.Friend;
import com.friendsocial.Backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// This will have all of the resources for the API

// API LAYER
// -
@RestController
@RequestMapping(path="api/v1/posts")
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

  @GetMapping(path = "{profileId}")
  public List<Post> getFriends(@PathVariable("profileId") Long id) {
    return postService.getPostsOfProfileById(id);
  }

  // POST (ADD) A Post
  // @RequestBody because we are taking the profile that comes from the client. Take request and map to profile
  @PostMapping(path = "{profileId}")
  public void createPost(@PathVariable("profileId") Long profileId, @RequestBody Post postRequest) {
    // Save the post entity
    postService.addNewPost(profileId, postRequest);
  }

  // DELETE A Post
  // pass the postId within the path. Grab the post ID with @PathVariable
  @DeleteMapping(path = "{postId}")
  public void deletePost(@PathVariable("postId") Long id) {
    postService.deletePost(id);
  }
}
