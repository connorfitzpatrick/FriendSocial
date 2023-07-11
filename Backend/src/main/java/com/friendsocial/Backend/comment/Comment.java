package com.friendsocial.Backend.comment;

import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.profile.Profile;
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

  // Store a foreign key instead of the entire profile object
  @Column(name = "profile_id")
  private Long profileId;

  @ManyToOne
  @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
  private Profile profile;

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
          Long profileId,
          Long postId,
          String content,
          Instant timestamp
  ) {
    this.profileId = profileId;
    this.postId = postId;
    this.content = content;
    this.timestamp = timestamp;
  }

  public Comment(
          Long id,
          Long profileId,
          Long postId,
          String content,
          Instant timestamp
  ) {
    this.id = id;
    this.profileId = profileId;
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

  public Long getProfileId() {
    return profileId;
  }

  public void setProfileId(Long profileId) {
    this.profileId = profileId;
  }

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
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
            ", profileId=" + profileId +
            ", postId=" + postId +
            ", content='" + content + '\'' +
            ", timestamp=" + timestamp +
            '}';
  }
}
