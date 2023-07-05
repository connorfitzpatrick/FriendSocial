package com.friendsocial.Backend.post;

// SERVICE LAYER
//   - Handles business logic

import com.friendsocial.Backend.profile.Profile;
import com.friendsocial.Backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Service allows Spring to detect beans (instances of objects) automatically.
@Service
public class PostService {
  private final PostRepository postRepository;
  private final ProfileRepository profileRepository;

  @Autowired
  public PostService(PostRepository postRepository, ProfileRepository profileRepository) {
    this.postRepository = postRepository;
    this.profileRepository = profileRepository;
  }

  // Business logic of getting all profiles. Just get them all.
  public List<Post> getPosts() {
    return postRepository.findAll();
  }

  // Business logic of Posting (adding) new profile. Do not add if email already in use.
  public void addNewPost(Long profileId, Post postRequest) {
    Optional<Profile> profileOptional = profileRepository.findById(profileId);
    // Add to Profile table
    if (profileOptional.isEmpty()) {
      // Handle case when profile is not found
      throw new IllegalArgumentException("Profile not found");
    }

    Profile profile = profileOptional.get();
//    postRequest.setProfile(profile);
    postRequest.setProfileId(profileId);
    profile.addPost(postRequest); // Associate post with profile
    postRepository.save(postRequest);
  }

  // Business logic of deleting a profile. Check if it exists first.
  public void deletePost(Long postId) {
    boolean exists = postRepository.existsById(postId);
    if (!exists) {
      throw new IllegalStateException(
              "Profile with id " + postId + " does not exist"
      );
    }
    postRepository.deleteById(postId);
  }

}
