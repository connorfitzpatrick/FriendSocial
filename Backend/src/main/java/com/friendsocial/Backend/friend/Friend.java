package com.friendsocial.Backend.friend;

import com.friendsocial.Backend.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "friends")
public class Friend {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // @ManyToOne defines relationship with User Entities for user and their friend
  // @JoinTable specifies the name of the join table that will be created in the DB to store relationship
  // @JoinColumns specifies the foreign key that references the Friend entity
  // @InverseJoinColumns specifies the foreign key that references User entity
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "friend_id")
  private Long friendId;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "friend_id", referencedColumnName = "user_id", insertable = false, updatable = false)
  private User friend;

//  @ManyToOne
//  @JoinColumn(name = "user_id", insertable = false, updatable = false)
//  private User user;
//
//  @ManyToOne
//  @JoinColumn(name = "friend_id", insertable = false, updatable = false)
//  private User friend;
  //private User friend;

  @Column(name = "start_date")
  private LocalDate startDate;

  public Friend() {

  }

  public Friend(Long userId, Long friendId, LocalDate startDate) {
    this.userId = userId;
    this.friendId = friendId;
    this.startDate = startDate;
  }

  public Long getId() {
    return id;
  }

  public void setId() {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getFriendId() {
    return friendId;
  }

  public void setFriendId(Long friendId) {
    this.friendId = friendId;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }
}
