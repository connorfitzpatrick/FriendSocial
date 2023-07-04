package com.friendsocial.Backend.profile;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

  public Profile getProfileById(Long id) {
    Optional<Profile> profileOptional = profileRepository.findById(id);
    // If another profile has this email, throw error
    if (!profileOptional.isPresent()) {
      throw new IllegalStateException("Profile is not in database");
    }
    return profileOptional.get();
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

  // Business logic of modifying a profile.
  // @Transactional's usage means we don't have to use query's. Entity goes into a managed state.
  @Transactional
  public void updateProfile(Long profileId, String email, String firstName) {
    // Check if profile with that ID exists, otherwise throw exception
    Profile profile = profileRepository.findById(profileId)
            .orElseThrow(() -> new IllegalStateException(
                    "Profile with id " + profileId + " does not exist"
            ));

    // Business logic for changing email
    // if email provided is not null, email length is greater than 0, and different from the current email,
    // set the email as the new provided one.
    if (email != null &&
            email.length() > 0 &&
            !Objects.equals(profile.getEmail(), email)) {
      // Check that the email hasn't been taken
      Optional<Profile> profileOptional = profileRepository
              .findProfileByEmail(email);
      if (profileOptional.isPresent()) {
        throw new IllegalStateException("email taken");
      }
      profile.setEmail(email);
    }

    // Business logic for changing email
    if (firstName != null &&
        firstName.length() > 0 &&
        !Objects.equals(profile.getFirstName(), firstName)) {
      profile.setFirstName(firstName);
    }
  }
}
