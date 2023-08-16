package com.friendsocial.Backend.friend;

import com.friendsocial.Backend.user.User;
import com.friendsocial.Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendService {
  private final FriendRepository friendRepository;
  private final UserRepository userRepository;

  @Autowired
  public FriendService(FriendRepository friendRepository, UserRepository userRepository) {
    this.friendRepository = friendRepository;
    this.userRepository = userRepository;
  }

  // Business logic of getting all friends. Just get them all.
  public List<Friend> getFriends() {
    return friendRepository.findAll();
  }

  // Business logic of getting all friendships that are associated with an user
  public List<Friend> getFriendsOfUserById(Long userId) {
    List<Friend> friendList = friendRepository.findFriendsOfUserId(userId);
    if (friendList.isEmpty()){
      throw new IllegalArgumentException("No friendships found");
    }
    return friendList;
  }

  public Friend getIsFriendByIds(Long userId, Long friendId) {
    return friendRepository.findIsFriendByIds(userId, friendId);
  }

  // Business logic of Posting (adding) new user. Do not add if email already in use.
  public Friend addNewFriend(Long userId, Long friendId, Friend friendRequest) {
    Optional<User> userOptional = userRepository.findById(userId);
    Optional<User> friendOptional = userRepository.findById(friendId);
    // Add to User table
    if (userOptional.isEmpty() || friendOptional.isEmpty()) {
      // Handle case when user is not found
      throw new IllegalArgumentException("Friendship not found");
    }

    // Will be needed to associate this friendship with the user
    User user = userOptional.get();
    User friend = friendOptional.get();
    // add foreign keys, otherwise they will return null
    friendRequest.setUser(user);
    friendRequest.setFriend(friend);
    // Associate friendship with user
    friend.addFriend(friendRequest);
    return friendRepository.save(friendRequest);
  }

  // Business logic of deleting a user. Check if it exists first.
  public void deleteFriend(Long friendshipId) {
    boolean exists = friendRepository.existsById(friendshipId);
    if (!exists) {
      throw new IllegalStateException(
              "User with id " + friendshipId + " does not exist"
      );
    }

    Optional<Friend> user = friendRepository.findById(friendshipId);
    long userId = user.get().getUser().getId();
    long friendId = user.get().getFriend().getId();


//    friendRepository.deleteReferencesInUserFriendsTable(userId, friendId);
    friendRepository.deleteById(friendshipId);
  }

}
