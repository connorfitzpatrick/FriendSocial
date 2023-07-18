package com.friendsocial.Backend.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            .findUserByEmail(user.getEmail());
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
  public void updateUser(Long userId, String email, String firstName) {
    // Check if user with that ID exists, otherwise throw exception
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalStateException(
                    "User with id " + userId + " does not exist"
            ));

    // Business logic for changing email
    // if email provided is not null, email length is greater than 0, and different from the current email,
    // set the email as the new provided one.
    if (email != null &&
            email.length() > 0 &&
            !Objects.equals(user.getEmail(), email)) {
      // Check that the email hasn't been taken
      Optional<User> userOptional = userRepository
              .findUserByEmail(email);
      if (userOptional.isPresent()) {
        throw new IllegalStateException("email taken");
      }
      user.setEmail(email);
    }

    // Business logic for changing email
    if (firstName != null &&
        firstName.length() > 0 &&
        !Objects.equals(user.getFirstName(), firstName)) {
      user.setFirstName(firstName);
    }
  }
}
