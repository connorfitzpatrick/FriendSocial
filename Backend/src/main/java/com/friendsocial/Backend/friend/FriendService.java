package com.friendsocial.Backend.friend;

import com.friendsocial.Backend.user.User;
import com.friendsocial.Backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
      Logger.getLogger(FriendService.class.getName()).log(Level.INFO, "No likes found on post with userId: " + userId);
      return new ArrayList<>();
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

    // Check if user or friend exists
    if (userOptional.isEmpty()) {
      Logger.getLogger(FriendService.class.getName()).log(Level.INFO, "User with with userId: " + userId + " does not exist");
      throw new IllegalArgumentException("Invalid user ID provided");
    }
    if (friendOptional.isEmpty()) {
      Logger.getLogger(FriendService.class.getName()).log(Level.INFO, "User with with userId: " + friendId + " does not exist");
      throw new IllegalArgumentException("Invalid user ID provided");
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
              "Friendship with id " + friendshipId + " does not exist"
      );
    }

    Optional<Friend> user = friendRepository.findById(friendshipId);
    friendRepository.deleteById(friendshipId);
  }

}
