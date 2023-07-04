package com.friendsocial.Backend.profile;

import com.friendsocial.Backend.post.Post;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

// Map Profile class to database (for hibernate). Entity represents a table. An instance of an entity
// represents a row in the table.
@Entity
// Specify the table name in the database
@Table(name="profiles")
public class Profile {
  // Defines the primary key
  @Id
  @Column(name = "profile_id")
  @SequenceGenerator(
          // generated sequence name
          name = "profiles_sequence",
          // database's sequence name
          sequenceName = "profiles_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.IDENTITY,
          generator = "profiles_sequence"
  )
  private Long id;

  @Column(length=50, nullable=false, unique=true)
  private String email;

  @Column(length=50, nullable=false, unique=true)
  private String username;

  @Column(nullable=false)
  private String password;

  @Column(name="dob", nullable=false)
  private LocalDate dob;

  // @Transient means we will ignore this column, since we can calculate it on our own using DOB
  // Will no longer be stored in DB
  @Transient
  private Integer age;

  @Column(name = "first_name", length=50, nullable = false)
  private String firstName;

  @Column(name = "last_name", length=50, nullable = false)
  private String lastName;

  @Column(name = "profile_picture")
  private String profilePic;

  @Column()
  private String bio;

  @Column(name = "join_timestamp", nullable = false)
  private LocalDateTime dateJoined;

  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> posts = new ArrayList<>();

  ///////////////////
  /// Contructors ///
  ///////////////////
  public Profile() {

  }

  public Profile(Long id,
                 String email,
                 String username,
                 String password,
                 LocalDate dob,
                 String firstName,
                 String lastName,
                 String profilePic,
                 String bio,
                 LocalDateTime dateJoined) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.password = password;
    this.dob = dob;
    this.firstName = firstName;
    this.lastName = lastName;
    this.profilePic = profilePic;
    this.bio = bio;
    this.dateJoined = dateJoined;
  }

  public Profile(String email,
                 String username,
                 String password,
                 LocalDate dob,
                 String firstName,
                 String lastName,
                 String profilePic,
                 String bio,
                 LocalDateTime dateJoined) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.dob = dob;
    this.firstName = firstName;
    this.lastName = lastName;
    this.profilePic = profilePic;
    this.bio = bio;
    this.dateJoined = dateJoined;
  }

  ///////////////////////////
  /// Getters and Setters ///
  ///////////////////////////
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

  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public int getAge() {
    return Period.between(this.dob, LocalDate.now()).getYears();
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

  /////////////////
  /// To String ///
  /////////////////
  @Override
  public String toString() {
    return "Profile{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", dob='" + dob + '\'' +
            ", age=" + age +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", profilePic='" + profilePic + '\'' +
            ", bio='" + bio + '\'' +
            ", dateJoined=" + dateJoined +
            '}';
  }
}
