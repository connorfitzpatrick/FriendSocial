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

  // Business logic of getting all profiles. Just get them all.
  public List<Profile> getProfiles() {
    return profileRepository.findAll();
  }

  // Business logic of Posting (adding) new profile. Do not add if email already in use.
  public void addNewProfile(Profile profile) {
    Optional<Profile> profileOptional = profileRepository
      .findProfileByEmail(profile.getEmail());
    // If another profile has this email, throw error
    if (profileOptional.isPresent()) {
      throw new IllegalStateException("Another Profile is Already Using This Email");
    }
    // Add to Profile table
    profileRepository.save(profile);
  }

  // Business logic of deleting a profile. Check if it exists first.
  public void deleteProfile(Long profileId) {
    boolean exists = profileRepository.existsById(profileId);
    if (!exists) {
      throw new IllegalStateException(
              "Profile with id " + profileId + " does not exist"
      );
    }
    profileRepository.deleteById(profileId);
  }
}
