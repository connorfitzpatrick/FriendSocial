package com.friendsocial.Backend.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

// Data Access Layer
//   - Handles data access / database interaction
@Repository
public interface ProfileRepository
  extends JpaRepository<Profile, Long> {
    // Custom function to find profile by email. Transforms to `SELECT * FROM profiles WHERE email = ?`
    @Query("SELECT p FROM Profile p WHERE p.email = ?1")
    Optional<Profile> findProfileByEmail(String email);
}


