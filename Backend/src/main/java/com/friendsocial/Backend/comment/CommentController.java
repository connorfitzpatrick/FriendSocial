package com.friendsocial.Backend.comment;

import com.friendsocial.Backend.post.PostRepository;
import com.friendsocial.Backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/comments")
public class CommentController {
  private final CommentService commentService;

  @Autowired
  private ProfileRepository profileRepository;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  // GET ALL POSTS
  // Get mapping because we want to get something out from our server
  @GetMapping
  public List<Comment> getComments() {
    return commentService.getComments();
  }

  @GetMapping(path = "{postId}")
  public List<Comment> getCommentsByPost(@PathVariable("{postId}") Long id) {
    return commentService.getCommentsOfPostById(id);
  }

}
