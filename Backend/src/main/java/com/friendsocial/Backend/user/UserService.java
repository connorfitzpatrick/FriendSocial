package com.friendsocial.Backend.user;

import com.friendsocial.Backend.elasticsearch.ElasticsearchUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// SERVICE LAYER
//   - Handles business logic

// @Service allows Spring to detect beans (instances of objects) automatically.
@Service
public class UserService {
  private final UserRepository userRepository;
  private final ElasticsearchUserService elasticsearchUserService; // Inject the Elasticsearch service

  @Autowired
  public UserService(UserRepository userRepository, ElasticsearchUserService elasticsearchUserService) {
    this.userRepository = userRepository;
    this.elasticsearchUserService = elasticsearchUserService;
  }

  // Business logic of getting all users. Just get them all.
  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public User getUserById(Long id) {
    Optional<User> userOptional = userRepository.findById(id);
    // If another user has this email, throw error
    if (!userOptional.isPresent()) {
      throw new IllegalStateException("User is not in database");
    }
    return userOptional.get();
  }

  // this is called when someone registers or logs in with their email
  public User getUserByUsername(String username) {
    Optional<User> userOptional = userRepository.findUserByUsername(username);
    // If another user has this email, throw error
    if (!userOptional.isPresent()) {
      throw new IllegalStateException("User with username " + username + " is not in database");
    }
    return userOptional.get();
  }

  public User getUserByHandle(String handle) {
    Optional<User> userOptional = userRepository.findUserByHandle(handle);
    // If another user has this email, throw error
    if (!userOptional.isPresent()) {
      throw new IllegalStateException("User with handle " + handle + " is not in database");
    }
    return userOptional.get();
  }

  // Business logic of Posting (adding) new user. Do not add if email already in use.
  public void addNewUser(User user) {
    Optional<User> userOptional = userRepository
            .findUserByUsername(user.getUsername());
    // If another user has this email, throw error
    if (userOptional.isPresent()) {
      throw new IllegalStateException("Another User is Already Using This Email");
    }
    // Add to User table
    User newUser = userRepository.save(user);

    try {
      // Index the updated user in Elasticsearch
      elasticsearchUserService.indexUser(newUser);
    } catch (IOException e) {
      System.out.println("ERROR IOException when trying to index user " + newUser);
    }
  }

  // Business logic of modifying a user.
  // @Transactional's usage means we don't have to use query's. Entity goes into a managed state.
  @Transactional
  public void updateUser(Long userId, User updatedUser) {
    // Check if user with that ID exists, otherwise throw exception
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                    "User with id " + userId + " does not exist"
            ));
    // Copy non-null properties from updatedUser to existingUser
    BeanUtils.copyProperties(updatedUser, user);
    // Save the updated user back to the database
    User modifiedUser = userRepository.save(user);

    try {
      // Index the updated user in Elasticsearch
      elasticsearchUserService.indexUser(modifiedUser);
    } catch (IOException e) {
      System.out.println("ERROR IOException when trying to index user " + modifiedUser);
    }
  }

  // Business logic of deleting a user. Check if it exists first.
  public void deleteUser(Long userId) {
    boolean exists = userRepository.existsById(userId);
    if (!exists) {
      throw new IllegalStateException(
              "User with id " + userId + " does not exist"
      );
    }
    userRepository.deleteById(userId);
  }

  public List<User> searchUsers(String query) {
    try {
      return elasticsearchUserService.searchUsers(query);
    } catch (IOException e) {
      // Handle Elasticsearch search exception
      return new ArrayList<>(); // Return an empty list or handle the exception accordingly
    }
  }

  public List<User> searchUsersByHandle(String handle) {
    try {
      // First, search users in Elasticsearch
      List<User> elasticSearchResults = elasticsearchUserService.searchUsers(handle);

      // If there are no results in Elasticsearch, fall back to database search
      if (elasticSearchResults.isEmpty()) {
        return userRepository.findByHandleStartingWith(handle);
      }
      return elasticSearchResults;
    } catch (IOException e) {
      // Handle Elasticsearch search exception
      return new ArrayList<>(); // Return an empty list or handle the exception accordingly
    }
  }
}
