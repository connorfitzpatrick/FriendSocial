package com.friendsocial.Backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedOrigin("http://localhost:4200"); // Replace with the URL of your Angular frontend
    config.addAllowedMethod("*"); // Allow all HTTP methods
    config.addAllowedHeader("*"); // Allow all headers
    source.registerCorsConfiguration("/api/**", config); // Replace "/api/**" with your API endpoint path
    return new CorsFilter(source);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/v1/posts") // Replace "/api/**" with your API endpoint path
            .allowedOrigins("http://localhost:4200/home") // Replace with the URL of your Angular frontend
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify the allowed HTTP methods
            .allowedHeaders("*"); // Allow all headers
  }
}
