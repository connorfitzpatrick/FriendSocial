package com.friendsocial.Backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.friendsocial.Backend.comment.Comment;
import com.friendsocial.Backend.friend.Friend;
import com.friendsocial.Backend.like.Like;
import com.friendsocial.Backend.post.Post;
import jakarta.persistence.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Map UserUser class to database (for hibernate). Entity represents a table. An instance of an entity
// represents a row in the table.
// Specify the table name in the database
@Entity
// Specify the table name in the database
@Table(name="users")
@Document(indexName = "user_index") // Specify the index name
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
  private String username;

  @Column(length=50, nullable=false, unique=true)
  private String handle;

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
  private Instant dateJoined;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private Set<Post> posts;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private Set<Comment> comments;

  @OneToMany(mappedBy = "user")
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
              String username,
              String handle,
              String password,
              LocalDate dob,
              String firstName,
              String lastName,
              String userPic,
              String bio,
              Instant dateJoined,
              Role role
  )
  {
    this.id = id;
    this.username = username;
    this.handle = handle;
    this.password = password;
    this.dob = dob;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userPic = userPic;
    this.bio = bio;
    this.dateJoined = dateJoined;
    this.role = role;
  }

  public User(String username,
              String handle,
              String password,
              LocalDate dob,
              String firstName,
              String lastName,
              String userPic,
              String bio,
              Instant dateJoined,
              Role role
  ) {
    this.username = username;
    this.handle = handle;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getHandle() {
    return handle;
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

  public void setHandle(String handle) {
    this.handle = handle;
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

  public Instant getDateJoined() {
    return dateJoined;
  }

  public void setDateJoined(Instant dateJoined) {
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

  public void addFriend(Friend friend) {
    if (friends == null) {
      friends = new HashSet<>();
    }
    friends.add(friend);
  }

  public void removeFriend(Friend friend) {
    friends.remove(friend);
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

  /////////////////
  /// To String ///
  /////////////////
  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", handle='" + handle + '\'' +
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
