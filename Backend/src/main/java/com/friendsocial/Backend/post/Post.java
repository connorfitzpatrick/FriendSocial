package com.friendsocial.Backend.post;

import com.friendsocial.Backend.profile.Profile;
import com.friendsocial.Backend.profile.ProfileRepository;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Optional;

// Map Profile class to database (for hibernate). Entity represents a table. An instance of an entity
// represents a row in the table.
@Entity
// Specify the table name in the database
@Table(name="posts")
public class Post {
  // Defines the primary key
  @Id
  @Column(name = "post_id")
  @SequenceGenerator(
          // generated sequence name
          name = "posts_sequence",
          // database's sequence name
          sequenceName = "posts_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.IDENTITY,
          generator = "posts_sequence"
  )
  private Long id;

  @ManyToOne
  @JoinColumn(name = "profile_id", nullable = false)
  private Profile profile;

  @Column(length=50, nullable=false)
  private String postType;

  @Column(length=255)
  private String content;

  @Column(nullable = false)
  private Instant timestamp;

  @Column(name = "image_url")
  private String imageUrl;

  ///////////////////
  /// Contructors ///
  ///////////////////
  public Post() {

  }

  public Post(
          Profile profile,
          String postType,
          String content,
          Instant timestamp,
          String imageUrl
  ) {
    this.profile = profile;
    this.postType = postType;
    this.content = content;
    this.timestamp = timestamp;
    this.imageUrl = imageUrl;
  }

  public Post(
          Long id,
          Profile profile,
          String postType,
          String content,
          Instant timestamp,
          String imageUrl
          ) {
    this.id = id;
    this.profile = profile;
    this.postType = postType;
    this.content = content;
    this.timestamp = timestamp;
    this.imageUrl = imageUrl;
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

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profileId) {
    this.profile = profile;
  }

  public String getPostType() {
    return postType;
  }

  public void setPostType(String postType) {
    this.postType = postType;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  /////////////////
  /// To String ///
  /////////////////
  @Override
  public String toString() {
    return "Post{" +
            "id=" + id +
            ", profile=" + profile.getId() +
            ", postType='" + postType + '\'' +
            ", content='" + content + '\'' +
            ", timestamp=" + timestamp +
            ", imageUrl='" + imageUrl + '\'' +
            '}';
  }
}


