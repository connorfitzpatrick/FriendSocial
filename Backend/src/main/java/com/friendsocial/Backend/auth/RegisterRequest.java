package com.friendsocial.Backend.auth;

import com.friendsocial.Backend.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String email;
  private String username;
  private String password;
  private LocalDate dob;
  private String firstName;
  private String lastName;
  private String userPic;
  private String bio;
  private Instant dateJoined;
  private Role role;
}
