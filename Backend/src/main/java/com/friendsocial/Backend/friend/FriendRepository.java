package com.friendsocial.Backend.friend;

import com.friendsocial.Backend.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository
        extends JpaRepository<Friend, Long> {
  // Custom function to find profile by email. Transforms to `SELECT * FROM profiles WHERE email = ?`
//  @Query("SELECT p FROM Profile p WHERE p.email = ?1")
//  Optional<Profile> findProfileByEmail(String email);
//
//  @Query("SELECT p FROM Profile p WHERE p.id = ?1")
//  Optional<Profile> findById(Long id);
}
