package com.friendsocial.Backend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This will have all of the resources for the API

// API LAYER
// -

// UserController is used, but you need to go to that URL.
@RestController
@RequestMapping(path="api/v1/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    /*
      This here should be avoided in favor of dependency injection.
      this.userService = new UserService();

      @Autowired above instantiates a userService and injects it into the constructor.
     */
    this.userService = userService;
  }

  // GET ALL USERS
  // Get mapping because we want to get something out from our server
  @GetMapping()
  public List<User> getUsers() {
    return userService.getUsers();
  }

  // GET ONE USER
  @GetMapping(path = "{userId}")
  public User getOneUserById(@PathVariable("userId") Long id) {
    return userService.getUserById(id);
  }

//  // GetUserPicById
//  @GetMapping(path = "{userId}/user_pic")
//  public ResponseEntity<Resource> getUserPic(@PathVariable Long id) {
//    String imagePath = userService.getUserPicPathById(id);
//    Resource imageResource = userService.loadUserPic(imagePath);
//    if (imageResource != null && imageResource.exists()) {
//      // Set the appropriate headers for the image response
//      System.out.println("IMAGE FOUND!");
//      HttpHeaders headers = new HttpHeaders();
//      headers.setContentType(MediaType.IMAGE_JPEG);
//      try {
//        // Read the image data from the resource and return it in the response
//        InputStream inputStream = imageResource.getInputStream();
//        return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
//      } catch (IOException e) {
//        // Handle the error if image data cannot be read
//        e.printStackTrace();
//      }
//    }
//    // Return an error response if the image is not found
//    return ResponseEntity.notFound().build();
//  }

  // POST (ADD) A USER
  // @RequestBody because we are taking the user that comes from the client. Take request and map to user
  @PostMapping
  public void registerNewUser(@RequestBody User user) {
    userService.addNewUser(user);
  }

  // DELETE A USER
  // pass the userId within the path. Grab the student ID with @PathVariable
  @DeleteMapping(path = "{userId}")
  public void deleteUser(@PathVariable("userId") Long id) {
    userService.deleteUser(id);
  }

  @PutMapping(path = "{userId}")
  public void updateUser(
          @PathVariable("userId") Long id,
          @RequestParam(required = false) String email,
          @RequestParam(required = false) String firstName) {
    userService.updateUser(id, email, firstName);
  }


}
