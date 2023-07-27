package com.friendsocial.Backend.post;

import com.friendsocial.Backend.user.UserRepository;
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
  private UserRepository userRepository;


  @Autowired
  public PostController(PostService postService) {
    /*
      This here should be avoided in favor of dependency injection.
      this.postService = new PostService();

      @Autowired above instantiates a userService and injects it into the constructor.
     */
    this.postService = postService;
  }

  // GET ALL POSTS
  // Get mapping because we want to get something out from our server
  @GetMapping
  public List<Object[]> getPosts() {
    return postService.getPosts();
  }

  @GetMapping(path = "{userId}")
  public List<Object[]> getFriends(@PathVariable("userId") Long id) {
    return postService.getPostsOfUserById(id);
  }

  // POST (ADD) A Post
  // @RequestBody because we are taking the user that comes from the client. Take request and map to user
  @PostMapping(path = "{userId}")
  public void createPost(@PathVariable("userId") Long userId, @RequestBody Post postRequest) {
    // Save the post entity
    postService.addNewPost(userId, postRequest);
  }

  // DELETE A Post
  // pass the postId within the path. Grab the post ID with @PathVariable
  @DeleteMapping(path = "{postId}")
  public void deletePost(@PathVariable("postId") Long id) {
    postService.deletePost(id);
  }
}
