package com.friendsocial.Backend.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {
  private static final String CONFIG_FILE = "key_config.json";
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String getSecretKey() {
    try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
      JsonNode root = objectMapper.readTree(inputStream);
      return root.get("SECRET_KEY").asText();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}

