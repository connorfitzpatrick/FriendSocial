package com.friendsocial.Backend.comment;

import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.post.PostRepository;
import com.friendsocial.Backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
  public List<Object[]> getCommentsByPost(@PathVariable("postId") Long id) {
    return commentService.getCommentsOfPostById(id);
  }

  @PostMapping(path = "{profileId}/{postId}")
  public void createComment(@PathVariable("profileId") Long profileId, @PathVariable("postId") Long postId, @RequestBody Comment commentRequest) {
    commentService.addNewComment(profileId, postId, commentRequest);
  }

  @DeleteMapping(path = "{commentId}")
  public void deleteComment(@PathVariable("commentId") Long commentId, @PathVariable("profileId") Long profileId) {
    commentService.deleteComment(commentId);
  }

}
