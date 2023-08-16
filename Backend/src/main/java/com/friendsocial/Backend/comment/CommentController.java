package com.friendsocial.Backend.comment;

import com.friendsocial.Backend.post.PostRepository;
import com.friendsocial.Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/comments")
public class CommentController {
  private final CommentService commentService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PostRepository postRepository;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  // GET ALL COMMENTS
  // Get mapping because we want to get something out from our server
  @GetMapping
  public List<Comment> getComments() {
    return commentService.getComments();
  }

  // GET LIKES OF A POST BY POST ID
  @GetMapping(path = "{postId}")
  public List<Object[]> getCommentsByPost(@PathVariable("postId") Long id) {
    List<Object[]> comments = commentService.getCommentsOfPostById(id);
    System.out.println("comments//");
    System.out.println(comments.get(0)[0]);
    return comments;
  }

  // GET TWO MOST RECENT COMMENTS
  @GetMapping("{postId}/recent-comments")
  public ResponseEntity<List<Object[]>> getRecentComments(@PathVariable Long postId) {
    List<Object[]> recentComments = commentService.getRecentComments(postId);
    return ResponseEntity.ok(recentComments);
  }

  @PostMapping(path = "{userId}/{postId}")
  public ResponseEntity<Comment> createComment(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId, @RequestBody Comment commentRequest) {
    Comment newComment = commentService.addNewComment(userId, postId, commentRequest);
    return ResponseEntity.ok(newComment);
  }

  @DeleteMapping(path = "{commentId}")
  public void deleteComment(@PathVariable("commentId") Long commentId) {
    commentService.deleteComment(commentId);
  }

}
