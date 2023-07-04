package com.friendsocial.Backend.friend;

import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.profile.Profile;
import com.friendsocial.Backend.profile.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendService {
  private final FriendRepository friendRepository;
  private final ProfileRepository profileRepository;

  @Autowired
  public FriendService(FriendRepository friendRepository, ProfileRepository profileRepository) {
    this.friendRepository = friendRepository;
    this.profileRepository = profileRepository;
  }

  // Business logic of getting all friends. Just get them all.
  public List<Friend> getFriends() {
    return friendRepository.findAll();
  }

  // Business logic of Posting (adding) new profile. Do not add if email already in use.
  public void addNewFriend(Long profileId, Long friendId, Friend friendRequest) {
    Optional<Profile> profileOptional = profileRepository.findById(profileId);
    Optional<Profile> friendOptional = profileRepository.findById(friendId);
    // Add to Profile table
    if (profileOptional.isEmpty() || friendOptional.isEmpty()) {
      // Handle case when profile is not found
      throw new IllegalArgumentException("Profile not found");
    }

    Profile profile = profileOptional.get();
    Profile friend = friendOptional.get();
    friendRequest.setProfile(profile);
    friendRequest.setFriend(friend);
    friend.addFriend(friendRequest); // Associate post with profile
    friendRepository.save(friendRequest);
  }

}
