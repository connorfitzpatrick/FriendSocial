package com.friendsocial.Backend.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.friendsocial.Backend.user.User;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
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
    objectMapper.registerModule(new JavaTimeModule());
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
    System.out.println(query);
    SearchRequest searchRequest = new SearchRequest("user_index");
    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

    MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(query, "handle")
            .type(MultiMatchQueryBuilder.Type.PHRASE_PREFIX);

    sourceBuilder.query(multiMatchQuery);
    searchRequest.source(sourceBuilder);
    System.out.println(searchRequest);

    SearchResponse response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
    System.out.println(response);

    List<User> users = Arrays.stream(response.getHits().getHits())
            .map(hit -> {
              try {
                // Convert JSON source to User object
                return objectMapper.readValue(hit.getSourceAsString(), User.class);
              } catch (IOException e) {
                // Handle exception
                System.out.println(e);
                return null;
              }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    System.out.println(users);
    return users;
  }

  public void recreateIndexWithCorrectMapping() throws IOException {
    // Delete the existing index
    DeleteIndexRequest deleteRequest = new DeleteIndexRequest("user_index");
    AcknowledgedResponse deleteResponse = elasticsearchClient.indices().delete(deleteRequest, RequestOptions.DEFAULT);

    // Recreate the index with correct mapping
    CreateIndexRequest createRequest = new CreateIndexRequest("user_index");
    createRequest.mapping("doc",
            "properties",
            "id", "type=long",
            "username", "type=text",
            "handle", "type=keyword",
            "password", "type=text",
            "dob", "type=date",
            "firstName", "type=text",
            "lastName", "type=text",
            "userPic", "type=text",
            "bio", "type=text",
            "dateJoined", "type=date",
            "role", "type=keyword"
    );

    CreateIndexResponse createResponse = elasticsearchClient.indices().create(createRequest, RequestOptions.DEFAULT);
  }
}