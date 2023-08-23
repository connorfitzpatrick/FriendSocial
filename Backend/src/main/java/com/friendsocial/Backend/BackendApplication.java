package com.friendsocial.Backend;

import com.friendsocial.Backend.elasticsearch.ElasticsearchUserService;
import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.unit.DataSize;

@SpringBootApplication
@ComponentScan(basePackages = "com.friendsocial.Backend")
public class BackendApplication {

	public static void main(String[] args) {
	  SpringApplication.run(BackendApplication.class, args);
	}

//  @Bean
//  public CommandLineRunner recreateIndexOnStartup(ElasticsearchUserService elasticsearchUserService) {
//    return args -> {
//      elasticsearchUserService.recreateIndexWithCorrectMapping();
//    };
//  }

  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    // Set the maximum size of a single file to be uploaded
    factory.setMaxFileSize(DataSize.parse("10MB"));

    // Set the maximum request size (including all files and form data)
    factory.setMaxRequestSize(DataSize.parse("10MB"));
    return factory.createMultipartConfig();
  }

}
