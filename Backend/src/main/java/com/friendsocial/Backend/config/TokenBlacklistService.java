package com.friendsocial.Backend.config;

import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

// In charge of keeping track of bad tokens, so deleted accounts cant get back in.
@Service
public class TokenBlacklistService {
  private final Set<String> blacklist = new HashSet<>();

  public void addToBlacklist(String token) {
    blacklist.add(token);
  }

  public boolean isBlacklisted(String token) {
    return blacklist.contains(token);
  }
}
