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

  // Business logic of getting all friendships that are associated with an profile
  public List<Friend> getFriendsOfProfileById(Long profileId) {
    List<Friend> friendList = friendRepository.findFriendsOfProfileId(profileId);
    if (friendList.isEmpty()){
      throw new IllegalArgumentException("No friendships found");
    }
    return friendList;
  }

  // Business logic of Posting (adding) new profile. Do not add if email already in use.
  public void addNewFriend(Long profileId, Long friendId, Friend friendRequest) {
    Optional<Profile> profileOptional = profileRepository.findById(profileId);
    Optional<Profile> friendOptional = profileRepository.findById(friendId);
    // Add to Profile table
    if (profileOptional.isEmpty() || friendOptional.isEmpty()) {
      // Handle case when profile is not found
      throw new IllegalArgumentException("Friendship not found");
    }

    // Will be needed to associate this friendship with the profile
    Profile friend = friendOptional.get();
    // add foreign keys, otherwise they will return null
    friendRequest.setProfileId(profileId);
    friendRequest.setFriendId(friendId);
    // Associate friendship with profile
    friend.addFriend(friendRequest);
    friendRepository.save(friendRequest);
  }

  // Business logic of deleting a profile. Check if it exists first.
  public void deleteFriend(Long friendshipId) {
    boolean exists = friendRepository.existsById(friendshipId);
    if (!exists) {
      throw new IllegalStateException(
              "Profile with id " + friendshipId + " does not exist"
      );
    }
    friendRepository.deleteById(friendshipId);
  }

}
