package com.friendsocial.Backend.elasticsearch;

import com.friendsocial.Backend.user.User;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ElasticsearchUserService {

  private final RestHighLevelClient elasticsearchClient;

  @Autowired
  public ElasticsearchUserService(RestHighLevelClient elasticsearchClient) {
    this.elasticsearchClient = elasticsearchClient;
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
}
