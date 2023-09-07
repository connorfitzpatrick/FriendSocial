package com.friendsocial.Backend.auth;

import com.friendsocial.Backend.config.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  private final TokenBlacklistService tokenBlacklistService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register (
          @RequestBody RegisterRequest request
  ) {
    System.out.println(request);
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate (
          @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh")
  public ResponseEntity<?> refreshTokens(
          @RequestHeader("Authorization") String authorizationHeader) {
    return ResponseEntity.ok(service.refresh(authorizationHeader));
  }

  @PostMapping("/logout")
  public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String authorizationHeader) {
    String token = authorizationHeader.replace("Bearer ", "");
    tokenBlacklistService.addToBlacklist(token);
    // will I have to add refresh token to blacklist as well?
    Map<String, String> response = new HashMap<>();
    response.put("message", "Logged out successfully.");

    return ResponseEntity.ok(response);
  }

}
