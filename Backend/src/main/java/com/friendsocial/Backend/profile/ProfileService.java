package com.friendsocial.Backend.profile;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// SERVICE LAYER
//   - Handles business logic

// @Service allows Spring to detect beans (instances of objects) automatically.
@Service
public class ProfileService {
  public List<Profile> getProfiles() {
    return List.of(
            new Profile(
                    "c@gmail.com",
                    "connorfitz429",
                    "svsb",
                    24,
                    "Connor",
                    "Fitzpatrick",
                    "./HERE.png",
                    "MY APP",
                    LocalDateTime.now()
            )
    );
  }
}
