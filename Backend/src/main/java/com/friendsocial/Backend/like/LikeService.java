package com.friendsocial.Backend.like;

import com.friendsocial.Backend.comment.Comment;
import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.post.PostRepository;
import com.friendsocial.Backend.profile.Profile;
import com.friendsocial.Backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
  private final LikeRepository likeRepository;
  private final PostRepository postRepository;
  private final ProfileRepository profileRepository;

  @Autowired
  public LikeService(LikeRepository likeRepository,
                     ProfileRepository profileRepository,
                     PostRepository postRepository) {
    this.likeRepository = likeRepository;
    this.profileRepository = profileRepository;
    this.postRepository = postRepository;
  }

  // Business logic of getting all likes. Just get them all.
  public List<Like> getLikes() {
    return likeRepository.findAll();
  }

  // Business logic of getting all likes that are associated with a post
  public List<Object[]> getLikesOfPostById(Long postId) {
    List<Object[]> likeList = likeRepository.findLikesOfPostId(postId);
    if (likeList.isEmpty()){
      throw new IllegalArgumentException("No comments found");
    }
    return likeList;
  }

  // Business logic of Posting (adding) new Like
  public void addNewLike(Long profileId, Long postId, Like likeRequest) {
    Optional<Post> postOptional = postRepository.findById(postId);
    Optional<Profile> profileOptional = profileRepository.findById(profileId);
    // Add to like table
    if (postOptional.isEmpty() || profileOptional.isEmpty()) {
      // Handle case when profile is not found
      throw new IllegalArgumentException("Post or Profile not found");
    }

    Post post = postOptional.get();
    Profile profile = profileOptional.get();;
    // Otherwise foreign key will be null
    likeRequest.setProfileId(profileId);
    likeRequest.setPostId(postId);
    post.addLike(likeRequest); // Associate comment with post
    profile.addLike(likeRequest); // Associate comment with profile
    likeRepository.save(likeRequest);
  }

  // Business logic of deleting a like. Check if it exists first.
  public void deleteLike(Long likeId) {
    boolean exists = likeRepository.existsById(likeId);
    if (!exists) {
      throw new IllegalStateException(
              "Like with id " + likeId + " does not exist"
      );
    }
    // Check if profile trying to delete is original poster or commenter
    likeRepository.deleteById(likeId);
  }
}
