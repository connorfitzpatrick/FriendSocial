package com.friendsocial.Backend.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.friendsocial.Backend.user.User;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.xcontent.XContentType;


import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ElasticsearchUserService {

  private final RestHighLevelClient elasticsearchClient;
  private final ObjectMapper objectMapper; // Inject ObjectMapper

  @Autowired
  public ElasticsearchUserService(RestHighLevelClient elasticsearchClient, ObjectMapper objectMapper) {
    this.elasticsearchClient = elasticsearchClient;
    this.objectMapper = objectMapper; // Inject ObjectMapper

  }

  public void indexUser(User user) throws IOException {
    IndexRequest request = new IndexRequest("user_index")
            .id(user.getId().toString())
            .source(convertUserToMap(user), XContentType.JSON);

    IndexResponse response = elasticsearchClient.index(request, RequestOptions.DEFAULT);
  }

  private Map<String, Object> convertUserToMap(User user) {
    // Convert user attributes to a Map
    Map<String, Object> userMap = new HashMap<>();
    userMap.put("id", user.getId());
    userMap.put("username", user.getUsername());
    userMap.put("handle", user.getHandle());
    userMap.put("dob", user.getDob().toString());
    userMap.put("firstName", user.getFirstName());
    userMap.put("lastName", user.getLastName());
    userMap.put("userPic", user.getUserPic());
    userMap.put("bio", user.getBio());
    userMap.put("dateJoined", user.getDateJoined().toString());
    return userMap;
  }

  public List<User> searchUsers(String query) throws IOException {
    SearchRequest searchRequest = new SearchRequest("user_index");
    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    // Build your search query based on the user's input
    sourceBuilder.query(QueryBuilders.matchQuery("handle", query));
    searchRequest.source(sourceBuilder);
    SearchResponse response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
    // Process the search response and convert to User objects
    // List<User> users = ...
    List<User> users = Arrays.stream(response.getHits().getHits())
            .map(hit -> {
              try {
                // Convert JSON source to User object
                return objectMapper.readValue(hit.getSourceAsString(), User.class);
              } catch (IOException e) {
                // Handle exception
                return null;
              }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    return users;
  }
}