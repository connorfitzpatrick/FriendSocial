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
  @ManyToOne
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @ManyToOne
  @JoinColumn(name = "Friend_id")
  private Profile friend;

  @Column(name = "start_date")
  private LocalDate startDate;

  public Friend() {

  }

  public Friend(Profile profile, Profile friend, LocalDate startDate) {
    this.profile = profile;
    this.friend = friend;
    this.startDate = startDate;
  }

  public Long getId() {
    return id;
  }

  public void setId() {
    this.id = id;
  }

  public Profile getProfile() {
    return profile;
  }

  public Long getProfileId() {
    return profile.getId();
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public Profile getFriend() {
    return friend;
  }

  public Long getFriendId() {
    return friend.getId();
  }

  public void setFriend(Profile friend) {
    this.friend = friend;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }
}
