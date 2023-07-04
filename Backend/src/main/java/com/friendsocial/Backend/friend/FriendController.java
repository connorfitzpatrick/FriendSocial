package com.friendsocial.Backend.friend;

import com.friendsocial.Backend.post.Post;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/friends")
public class FriendController {
  private final FriendService friendService;

  public FriendController(FriendService friendService) {
    this.friendService = friendService;
  }

  @GetMapping
  public List<Friend> getFriends() {
    return friendService.getFriends();
  }

  // POST (ADD) A Post
  // @RequestBody because we are taking the profile that comes from the client. Take request and map to profile
  @PostMapping(path = "{profileId}/{friendId}")
  public void createFriend(@PathVariable("profileId") Long profileId, @PathVariable("friendId") Long friendId, @RequestBody Friend friendRequest) {
    // Save the post entity
    friendService.addNewFriend(profileId, friendId, friendRequest);
  }
}

