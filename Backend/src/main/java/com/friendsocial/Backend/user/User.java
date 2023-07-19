package com.friendsocial.Backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.friendsocial.Backend.comment.Comment;
import com.friendsocial.Backend.friend.Friend;
import com.friendsocial.Backend.like.Like;
import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.user.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

// Map UserUser class to database (for hibernate). Entity represents a table. An instance of an entity
// represents a row in the table.
// Specify the table name in the database
@Entity
// Specify the table name in the database
@Table(name="users")
public class User implements UserDetails {
  // Defines the primary key
  @Id
  @Column(name = "user_id")
  @SequenceGenerator(
          // generated sequence name
          name = "users_sequence",
          // database's sequence name
          sequenceName = "users_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.IDENTITY,
          generator = "users_sequence"
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

  @Column(name = "first_name", length=50, nullable = false)
  private String firstName;

  @Column(name = "last_name", length=50, nullable = false)
  private String lastName;

  @Column(name = "user_picture")
  private String userPic;

  @Column()
  private String bio;

  @Column(name = "join_timestamp", nullable = false)
  private LocalDateTime dateJoined;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private Set<Post> posts;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private Set<Comment> comments;

  @ManyToMany
  @JoinTable(name = "user_friends",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "friend_id"))
  @JsonIgnore
  private Set<Friend> friends;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private Set<Like> likes;


  ///////////////////
  /// Contructors ///
  ///////////////////
  public User() {

  }

  public User(Long id,
              String email,
              String username,
              String password,
              LocalDate dob,
              String firstName,
              String lastName,
              String userPic,
              String bio,
              LocalDateTime dateJoined,
              Role role
  )
  {
    this.id = id;
    this.email = email;
    this.username = username;
    this.password = password;
    this.dob = dob;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userPic = userPic;
    this.bio = bio;
    this.dateJoined = dateJoined;
    this.role = role;
  }

  public User(String email,
              String username,
              String password,
              LocalDate dob,
              String firstName,
              String lastName,
              String userPic,
              String bio,
              LocalDateTime dateJoined,
              Role role
  ) {
    this.email = email;
    this.username = username;
    this.password = password;
    this.dob = dob;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userPic = userPic;
    this.bio = bio;
    this.dateJoined = dateJoined;
    this.role = role;
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
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void setUsername(String email) {
    this.email = email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
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

  public String getUserPic() {
    return userPic;
  }

  public void setUserPic(String userPic) {
    this.userPic = userPic;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public LocalDateTime getDateJoined() {
    return dateJoined;
  }

  public void setDateJoined(LocalDateTime dateJoined) {
    this.dateJoined = dateJoined;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Set<Post> getPosts() {
    return this.posts;
  }

  public void setPosts(Set<Post> posts) {
    this.posts = posts;
  }

  public void addPost(Post post) {
    posts.add(post);
  }

//  public Set<Friend> getFriends() {
//    return friends;
//  }
//
//  public void setFriends(Set<UserUser> friends) {
//    this.friends = friends;
//  }
//
  public void addFriend(Friend friend) {
    friends.add(friend);
  }

  public Set<Comment> getComments() {
    return this.comments;
  }

  public void setComments(Set<Comment> comments) {
    this.comments = comments;
  }

  public void addComment(Comment comment) {
    comments.add(comment);
  }

  public Set<Like> getLikes() {
    return this.likes;
  }

  public void setLikes(Set<Like> comments) {
    this.likes = likes;
  }

  public void addLike(Like like) {
    likes.add(like);
  }
//
//  public void removeFriend(Friend friend) {
//    friends.remove(friend);
//  }

  /////////////////
  /// To String ///
  /////////////////
  @Override
  public String toString() {
    return "UserUser{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", dob='" + dob + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", userPic='" + userPic + '\'' +
            ", bio='" + bio + '\'' +
            ", dateJoined=" + dateJoined +
            '}';
  }
}
