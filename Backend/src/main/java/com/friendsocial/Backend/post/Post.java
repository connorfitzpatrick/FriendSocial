package com.friendsocial.Backend.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.friendsocial.Backend.comment.Comment;
import com.friendsocial.Backend.like.Like;
import com.friendsocial.Backend.profile.Profile;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Set;

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

  // Store a foreign key instead of the entire post object
  @Column(name = "profile_id")
  private Long profileId;

  @ManyToOne
  @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
  private Profile profile;

  @Column(length=50, nullable=false)
  private String postType;

  @Column(length=255)
  private String content;

  @Column(nullable = false)
  private Instant timestamp;

  @Column(name = "image_url")
  private String imageUrl;

  @OneToMany(mappedBy = "post")
  @JsonIgnore
  private Set<Comment> comments;

  @OneToMany(mappedBy = "post")
  @JsonIgnore
  private Set<Like> likes;

  ///////////////////
  /// Contructors ///
  ///////////////////
  public Post() {

  }

  public Post(
          //Profile profile,
          Long profileId,
          String postType,
          String content,
          Instant timestamp,
          String imageUrl
  ) {
    this.profileId = profileId;
    this.postType = postType;
    this.content = content;
    this.timestamp = timestamp;
    this.imageUrl = imageUrl;
  }

  public Post(
          Long id,
          Long profileId,
          String postType,
          String content,
          Instant timestamp,
          String imageUrl
          ) {
    this.id = id;
    this.profileId = profileId;
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

  public Long getProfileId() {
    return profileId;
  }

  public void setProfileId(Long profileId) {
    this.profileId = profileId;
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
    return "Post{" +
            "id=" + id +
            ", profileId='" + profileId + '\'' +
            ", postType='" + postType + '\'' +
            ", content='" + content + '\'' +
            ", timestamp=" + timestamp +
            ", imageUrl='" + imageUrl + '\'' +
            '}';
  }
}


