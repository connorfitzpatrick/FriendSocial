package com.friendsocial.Backend.comment;

import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.post.PostRepository;
import com.friendsocial.Backend.profile.Profile;
import com.friendsocial.Backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final ProfileRepository profileRepository;

  @Autowired
  public CommentService(CommentRepository commentRepository,
                        PostRepository postRepository,
                        ProfileRepository profileRepository) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.profileRepository = profileRepository;
  }

  // Business logic of getting all comments. Just get them all.
  public List<Comment> getComments() {
    return commentRepository.findCommentsAndPostProfileInfo();
  }

  // Business logic of getting all comments that are associated with a post
  public List<Object[]> getCommentsOfPostById(Long postId) {
    List<Object[]> commentList = commentRepository.findCommentsOfPostId(postId);
    if (commentList.isEmpty()){
      throw new IllegalArgumentException("No comments found");
    }
    return commentList;
  }

  // Business logic of Posting (adding) new Comment
  public void addNewComment(Long profileId, Long postId, Comment commentRequest) {
    Optional<Post> postOptional = postRepository.findById(postId);
    Optional<Profile> profileOptional = profileRepository.findById(profileId);
    // Add to comment table
    if (postOptional.isEmpty() || profileOptional.isEmpty()) {
      // Handle case when profile is not found
      throw new IllegalArgumentException("Post or Profile not found");
    }

    Post post = postOptional.get();
    Profile profile = profileOptional.get();;
    // Otherwise foreign key will be null
    commentRequest.setProfileId(profileId);
    commentRequest.setPostId(postId);
    post.addComment(commentRequest); // Associate comment with post
    profile.addComment(commentRequest); // Associate comment with profile
    commentRepository.save(commentRequest);
  }

  // Business logic of deleting a comment. Check if it exists first.
  public void deleteComment(Long commentId) {
    boolean exists = commentRepository.existsById(commentId);
    if (!exists) {
      throw new IllegalStateException(
              "Comment with id " + commentId + " does not exist"
      );
    }
    // Check if profile trying to delete is original poster or commenter
    commentRepository.deleteById(commentId);
  }

}
