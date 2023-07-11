package com.friendsocial.Backend.comment;

import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.post.PostRepository;
import com.friendsocial.Backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

  // Business logic of getting all friendships that are associated with an profile
  public List<Comment> getCommentsOfPostById(Long postId) {
    List<Comment> commentList = commentRepository.findCommentsOfPostId(postId);
    if (commentList.isEmpty()){
      throw new IllegalArgumentException("No comments found");
    }
    return commentList;
  }


}
