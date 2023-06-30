package com.friendsocial.Backend.profile;

import java.time.LocalDateTime;

public class Profile {
  private Long id;
  private String email;
  private String username;
  private String password;
  private int age;
  private String firstName;
  private String lastName;
  private String profilePic;
  private String bio;
  private LocalDateTime dateJoined;

  public Profile() {

  }

  public Profile(Long id,
                 String email,
                 String username,
                 String password,
                 int age,
                 String firstName,
                 String lastName,
                 String profilePic,
                 String bio,
                 LocalDateTime dateJoined) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.password = password;
    this.age = age;
    this.firstName = firstName;
    this.lastName = lastName;
    this.profilePic = profilePic;
    this.bio = bio;
    this.dateJoined = dateJoined;
  }

  public Profile(String email,
                 String username,
                 String password,
                 int age,
                 String firstName,
                 String lastName,
                 String profilePic,
                 String bio,
                 LocalDateTime dateJoined) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.age = age;
    this.firstName = firstName;
    this.lastName = lastName;
    this.profilePic = profilePic;
    this.bio = bio;
    this.dateJoined = dateJoined;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getProfilePic() {
    return profilePic;
  }

  public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public LocalDateTime getdateJoined() {
    return dateJoined;
  }

  public void setDateJoined(LocalDateTime dateJoined) {
    this.dateJoined = dateJoined;
  }

  @Override
  public String toString() {
    return "Profile{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", age=" + age +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", profilePic='" + profilePic + '\'' +
            ", bio='" + bio + '\'' +
            ", dateJoined=" + dateJoined +
            '}';
  }
}
