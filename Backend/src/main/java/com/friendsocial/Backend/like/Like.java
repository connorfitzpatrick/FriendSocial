package com.friendsocial.Backend.like;

import com.friendsocial.Backend.post.Post;
import com.friendsocial.Backend.profile.Profile;
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

  @Column(nullable = false)
  private Instant timestamp;

  public Like() {

  }

  public Like(
          Long profileId,
          Long postId,
          Instant timestamp
  ) {
    this.profileId = profileId;
    this.postId = postId;
    this.timestamp = timestamp;
  }

  public Like(
          Long id,
          Long profileId,
          Long postId,
          Instant timestamp
  ) {
    this.id = id;
    this.profileId = profileId;
    this.postId = postId;
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
            ", profileId=" + profileId +
            ", postId=" + postId +
            ", timestamp=" + timestamp +
            '}';
  }

}
