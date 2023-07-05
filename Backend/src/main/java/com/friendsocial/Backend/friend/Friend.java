package com.friendsocial.Backend.friend;

import com.friendsocial.Backend.profile.Profile;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "friends")
public class Friend {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // @ManyToOne defines relationship with Profile Entities for profile and their friend
  // @JoinTable specifies the name of the join table that will be created in the DB to store relationship
  // @JoinColumns specifies the foreign key that references the Friend entity
  // @InverseJoinColumns specifies the foreign key that references Profile entity
  @Column(name = "profile_id")
  private Long profileId;

  @Column(name = "friend_id")
  private Long friendId;

  @ManyToOne
  @JoinColumn(name = "profile_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
  private Profile profile;

  @ManyToOne
  @JoinColumn(name = "friend_id", referencedColumnName = "profile_id", insertable = false, updatable = false)
  private Profile friend;

//  @ManyToOne
//  @JoinColumn(name = "profile_id", insertable = false, updatable = false)
//  private Profile profile;
//
//  @ManyToOne
//  @JoinColumn(name = "friend_id", insertable = false, updatable = false)
//  private Profile friend;
  //private Profile friend;

  @Column(name = "start_date")
  private LocalDate startDate;

  public Friend() {

  }

  public Friend(Long profileId, Long friendId, LocalDate startDate) {
    this.profileId = profileId;
    this.friendId = friendId;
    this.startDate = startDate;
  }

  public Long getId() {
    return id;
  }

  public void setId() {
    this.id = id;
  }

//  public Profile getProfile() {
//    return profile;
//  }
//
//  public void setProfile(Profile profile) {
//    this.profile = profile;
//  }

  public Long getProfileId() {
    return profileId;
  }

  public void setProfileId(Long profileId) {
    this.profileId = profileId;
  }

  public Long getFriendId() {
    return friendId;
  }

  public void setFriendId(Long friendId) {
    this.friendId = friendId;
  }

//  public Profile getFriend() {
//    return friend;
//  }
//
//  public void setFriend(Profile friend) {
//    this.friend = friend;
//  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }
}
