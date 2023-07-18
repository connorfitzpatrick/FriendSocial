package com.friendsocial.Backend.friend;

import com.friendsocial.Backend.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/friends")
public class FriendController {
  private final FriendService friendService;
  private final UserService userService;

  public FriendController(FriendService friendService, UserService userService) {
    this.friendService = friendService;
    this.userService = userService;
  }

  @GetMapping
  public List<Friend> getFriends() {
    return friendService.getFriends();
  }

  @GetMapping(path = "{userId}")
  public List<Friend> getFriends(@PathVariable("userId") Long id) {
    return friendService.getFriendsOfUserById(id);
  }

  // POST (ADD) A Post
  // @RequestBody because we are taking the user that comes from the client. Take request and map to user
  @PostMapping(path = "{userId}/{friendId}")
  public void createFriend(@PathVariable("userId") Long userId, @PathVariable("friendId") Long friendId, @RequestBody Friend friendRequest) {
    // Save the post entity
    friendService.addNewFriend(userId, friendId, friendRequest);
  }

  // DELETE A Post
  // pass the postId within the path. Grab the post ID with @PathVariable
  @DeleteMapping(path = "{friendshipId}")
  public void deletePost(@PathVariable("friendshipId") Long id) {
    friendService.deleteFriend(id);
  }
}

