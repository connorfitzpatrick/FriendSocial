package com.friendsocial.Backend.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// SERVICE LAYER
//   - Handles business logic

// @Service allows Spring to detect beans (instances of objects) automatically.
@Service
public class ProfileService {
  private final ProfileRepository profileRepository;

  @Autowired
  public ProfileService(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }

  public List<Profile> getProfiles() {
    return profileRepository.findAll();
  }

  public void addNewProfile(Profile profile) {
    Optional<Profile> profileOptional = profileRepository
      .findProfileByEmail(profile.getEmail());
    // If another profile has this email, throw error
    if (profileOptional.isPresent()) {
      throw new IllegalStateException("Another Profile is Already Using This Email");
    }
    System.out.println(profile);
    // Add to Profile table
    profileRepository.save(profile);
  }
}
