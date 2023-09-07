package com.friendsocial.Backend.auth;

import com.friendsocial.Backend.config.JwtService;
import com.friendsocial.Backend.config.TokenBlacklistService;
import com.friendsocial.Backend.user.User;
import com.friendsocial.Backend.user.UserRepository;
import com.friendsocial.Backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final TokenBlacklistService tokenBlacklistService;
  private final UserDetailsService userDetailsService;

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  @Autowired
  private final JwtService jwtService;
  @Autowired
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    User user = new User(
            request.getUsername(),
            request.getHandle(),
            passwordEncoder.encode(request.getPassword()),
            request.getDob(),
            request.getFirstName(),
            request.getLastName(),
            request.getUserPic(),
            request.getBio(),
            request.getDateJoined(),
            request.getRole()
    );
    userService.addNewUser(user);

    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    return AuthenticationResponse.builder()
            .authenticationToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );
    // grab the user by their username/email
    var user = userRepository.findUserByUsername(request.getUsername())
            .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    return AuthenticationResponse.builder()
            .authenticationToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  public Map<String, String> refresh(String authorizationHeader) {
    String refreshToken = authorizationHeader.replace("Bearer ", "");
    Map<String, String> response = new HashMap<>();
    if (refreshToken == null || tokenBlacklistService.isBlacklisted(refreshToken)) {
      response.put("message", "Refresh token null or not valid");
      return response;
    }
    // Extract the username from the refresh token
    String username = jwtService.extractUsername(refreshToken);

    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    String newAccessToken = jwtService.generateToken(userDetails);

    response.put("authenticationToken", newAccessToken);
    return response;

  }
}
