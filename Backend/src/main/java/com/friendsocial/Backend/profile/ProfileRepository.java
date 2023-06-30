package com.friendsocial.Backend.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

// Data Access Layer
//   - Handles data access / database interaction
@Repository
public interface ProfileRepository
  extends JpaRepository<Profile, Long> {
}
