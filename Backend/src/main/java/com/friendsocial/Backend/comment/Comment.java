package com.friendsocial.Backend.comment;

import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.user.User;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
// Specify the table name in the database
@Table(name="comments")
public class Comment {
  @Id
  @Column(name = "comment_id")
  @SequenceGenerator(
          // generated sequence name
          name = "comments_sequence",
          // database's sequence name
          sequenceName = "comments_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.IDENTITY,
          generator = "comments_sequence"
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

  @Column(nullable=false)
  private String content;

  @Column(nullable = false)
  private Instant timestamp;

  public Comment() {

  }

  public Comment(
          Long userId,
          Long postId,
          String content,
          Instant timestamp
  ) {
    this.userId = userId;
    this.postId = postId;
    this.content = content;
    this.timestamp = timestamp;
  }

  public Comment(
          Long id,
          Long userId,
          Long postId,
          String content,
          Instant timestamp
  ) {
    this.id = id;
    this.userId = userId;
    this.postId = postId;
    this.content = content;
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

  @Override
  public String toString() {
    return "Comment{" +
            "id=" + id +
            ", userId=" + userId +
            ", postId=" + postId +
            ", content='" + content + '\'' +
            ", timestamp=" + timestamp +
            '}';
  }
}
