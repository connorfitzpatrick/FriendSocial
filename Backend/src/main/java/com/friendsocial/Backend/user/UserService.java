package com.friendsocial.Backend.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// SERVICE LAYER
//   - Handles business logic

// @Service allows Spring to detect beans (instances of objects) automatically.
@Service
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
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

  public User getUserByHandle(String handle) {
    Optional<User> userOptional = userRepository.findUserByHandle(handle);
    // If another user has this email, throw error
    if (!userOptional.isPresent()) {
      throw new IllegalStateException("User is not in database");
    }
    return userOptional.get();
  }

//  public String getUserPicPathById(Long id) {
//    Optional<User> userOptional = userRepository.findById(id);
//    if (!userOptional.isPresent()) {
//      throw new IllegalStateException("User is not in database");
//    }
//    return userOptional.get().getUserPic();
//  }
//
//  public Resource loadUserPic(String imagePath) {
//    try {
//      Path path = Paths.get(imagePath);
//      Resource imageResource = new FileSystemResource(path);
//      return imageResource;
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    return null;
//  }

  // Business logic of Posting (adding) new user. Do not add if email already in use.
  public void addNewUser(User user) {
    Optional<User> userOptional = userRepository
            .findUserByUsername(user.getUsername());
    // If another user has this email, throw error
    if (userOptional.isPresent()) {
      throw new IllegalStateException("Another User is Already Using This Email");
    }
    // Add to User table
    userRepository.save(user);
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

  // Business logic of modifying a user.
  // @Transactional's usage means we don't have to use query's. Entity goes into a managed state.
  @Transactional
  public void updateUser(Long userId, User updatedUser) {
    // Check if user with that ID exists, otherwise throw exception
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                    "User with id " + userId + " does not exist"
            ));
    System.out.println("GETS TO SERVICE");
    // Copy non-null properties from updatedUser to existingUser
    BeanUtils.copyProperties(updatedUser, user);
    // Save the updated user back to the database
    System.out.println(updatedUser);
    System.out.println(user);
    userRepository.save(user);

    // if email provided is not null, email length is greater than 0, and different from the current email,
    // set the email as the new provided one.

  }
}
