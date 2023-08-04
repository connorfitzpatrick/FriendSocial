package com.friendsocial.Backend.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Data Access Layer
//   - Handles data access / database interaction
@Repository
public interface UserRepository
  extends JpaRepository<User, Long> {
    // Custom function to find user by email. Transforms to `SELECT * FROM users WHERE email = ?`
    @Query("SELECT p FROM User p WHERE p.username = ?1")
    Optional<User> findUserByUsername(String username);

    @Query("SELECT p FROM User p WHERE p.handle = ?1")
    Optional<User> findUserByHandle(String handle);
}


