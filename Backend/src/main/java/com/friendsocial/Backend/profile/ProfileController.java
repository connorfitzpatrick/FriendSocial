package com.friendsocial.Backend.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// This will have all of the resources for the API
@RestController
@RequestMapping(path="api/v1/profile")


// API LAYER
// -

// ProfileController is used, but you need to go to that URL.
public class ProfileController {

  private final ProfileService profileService;

  @Autowired
  public ProfileController(ProfileService profileService) {
    /*
      This here should be avoided in favor of dependency injection.
      this.profileService = new ProfileService();

      @Autowired above instantiates a profileService and injects it into the constructor.
     */
    this.profileService = profileService;
  }

  // Get mapping because we want to get something out from our server
  @GetMapping
  public List<Profile> getProfiles() {
    return profileService.getProfiles();
  }
}
