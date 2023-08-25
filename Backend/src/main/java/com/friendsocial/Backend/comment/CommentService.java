package com.friendsocial.Backend.comment;

import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.post.PostRepository;
import com.friendsocial.Backend.user.User;
import com.friendsocial.Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  @Autowired
  public CommentService(CommentRepository commentRepository,
                        PostRepository postRepository,
                        UserRepository userRepository) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  // Business logic of getting all comments. Just get them all.
  public List<Comment> getComments() {
    return commentRepository.findCommentsAndPostUserInfo();
  }

  // Business logic of getting all comments that are associated with a post
  public List<Object[]> getCommentsOfPostById(Long postId) {
    List<Object[]> commentList = commentRepository.findCommentsOfPostId(postId);
    if (commentList.isEmpty()){
      throw new IllegalArgumentException("No comments found");
    }
    return commentList;
  }

  public List<Object[]> getRecentComments(Long postId) {
    return commentRepository.findTop2ByPostIdOrderByTimestampDesc(postId);
  }

  // Business logic of Posting (adding) new Comment
  public Comment addNewComment(Long userId, Long postId, Comment commentRequest) {
    Optional<Post> postOptional = postRepository.findById(postId);
    Optional<User> userOptional = userRepository.findById(userId);
    // Add to comment table
    if (postOptional.isEmpty() || userOptional.isEmpty()) {
      // Handle case when user is not found
      throw new IllegalArgumentException("Post or User not found");
    }

    Post post = postOptional.get();
    User user = userOptional.get();;
    // Otherwise foreign key will be null
    commentRequest.setUserId(userId);
    commentRequest.setPostId(postId);
    post.addComment(commentRequest); // Associate comment with post
    user.addComment(commentRequest); // Associate comment with user
    return commentRepository.save(commentRequest);

  }

  // Business logic of deleting a comment. Check if it exists first.
  public void deleteComment(Long commentId) {
    boolean exists = commentRepository.existsById(commentId);
    if (!exists) {
      throw new IllegalStateException(
              "Comment with id " + commentId + " does not exist"
      );
    }
    // Check if user trying to delete is original poster or commenter
    commentRepository.deleteById(commentId);
  }

}
