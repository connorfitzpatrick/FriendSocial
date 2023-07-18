package com.friendsocial.Backend.like;

import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
// Specify the table name in the database
@Table(name="likes")
public class Like {
  @Id
  @Column(name = "like_id")
  @SequenceGenerator(
          // generated sequence name
          name = "likes_sequence",
          // database's sequence name
          sequenceName = "likes_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.IDENTITY,
          generator = "likes_sequence"
  )
  private Long id;

  // Store a foreign key instead of the entire user object
  @Column(name = "user_id")
  private Long userId;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  private User user;

  // Store a foreign key instead of the entire post object
  @Column(name = "post_id")
  private Long postId;

  @ManyToOne
  @JoinColumn(name = "post_id", referencedColumnName = "post_id", insertable = false, updatable = false)
  private Post post;

  @Column(nullable = false)
  private Instant timestamp;

  public Like() {

  }

  public Like(
          Long userId,
          Long postId,
          Instant timestamp
  ) {
    this.userId = userId;
    this.postId = postId;
    this.timestamp = timestamp;
  }

  public Like(
          Long id,
          Long userId,
          Long postId,
          Instant timestamp
  ) {
    this.id = id;
    this.userId = userId;
    this.postId = postId;
    this.timestamp = timestamp;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "Comment{" +
            "id=" + id +
            ", userId=" + userId +
            ", postId=" + postId +
            ", timestamp=" + timestamp +
            '}';
  }

}
