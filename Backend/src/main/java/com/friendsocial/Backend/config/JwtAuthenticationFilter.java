package com.friendsocial.Backend.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/* When a request comes in it is first processed in this class. If token is expired,

 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwtToken;
    final String userEmail;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      System.out.println("doFilterInternal");
      return;
    }
    jwtToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(jwtToken);
    System.out.println("User Email: " + userEmail);
    // If user does not have token yet...
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      // get user details from DB
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      // Check if user's token is valid or not
      try {
        if (jwtService.isTokenValid(jwtToken, userDetails)) {
          // if so, create UsernamePasswordAuthenticationToken and pass userDetails and authorities
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
          );
          // enforce authentication token with details of request
          authToken.setDetails(
                  new WebAuthenticationDetailsSource().buildDetails(request)
          );
          // set authentication token
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      } catch (ExpiredJwtException ex) {
        System.out.println("Token expired: " + ex.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }
}
