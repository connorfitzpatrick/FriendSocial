package com.friendsocial.Backend.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.friendsocial.Backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ElasticsearchController {

  private final ElasticsearchUserService elasticsearchUserService;

  @Autowired
  public ElasticsearchController(ElasticsearchUserService elasticsearchUserService) {
    this.elasticsearchUserService = elasticsearchUserService;
  }

  @GetMapping("/search")
  public ResponseEntity<List<User>> searchUsers(@RequestParam String query) {
    // Call your ElasticsearchUserService to search for users based on the query
    try {
      // Index the updated user in Elasticsearch
      System.out.println("Query: " + query);
      List<User> searchResults = elasticsearchUserService.searchUsers(query);
      System.out.println("searchResults");
      System.out.println(searchResults);
      return ResponseEntity.ok(searchResults);

    } catch (JsonProcessingException e) {
      // Handle Jackson serialization issues
      e.printStackTrace();
      List<User> u = null;
      return ResponseEntity.ok(u);
    } catch (IOException e) {
      // Handle other IO issues
      e.printStackTrace();
      List<User> u = null;
      return ResponseEntity.ok(u);

    } catch (Exception e) {
      // Handle other exceptions
      e.printStackTrace();
      List<User> u = null;
      return ResponseEntity.ok(u);

    }
  }
}
