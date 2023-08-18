package com.friendsocial.Backend.elasticsearch;

import com.friendsocial.Backend.user.User;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ElasticsearchUserSearchService {

  private final RestHighLevelClient elasticsearchClient;

  @Autowired
  public ElasticsearchUserSearchService(RestHighLevelClient elasticsearchClient) {
    this.elasticsearchClient = elasticsearchClient;
  }

  public List<User> searchUsers(String query) throws IOException {
    SearchRequest searchRequest = new SearchRequest("user_index");
    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

    // Build your search query based on the user's input
    sourceBuilder.query(QueryBuilders.matchQuery("field_name", query));

    searchRequest.source(sourceBuilder);

    SearchResponse response = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

    // Process the search response and convert to User objects
    // List<User> users = ...
    List<User> users = null;
    return users;
  }
}

