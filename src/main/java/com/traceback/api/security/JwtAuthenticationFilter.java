package com.traceback.api.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.traceback.api.entity.User;
import com.traceback.api.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Intercepts every API request to check for a valid JWT token.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            
        // 1. Look for the "Authorization" header in the request
        final String authHeader = request.getHeader("Authorization");
        
        // 2. If there's no header, or it doesn't start with "Bearer ", move along (let the bouncer block it later)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the token (Remove the "Bearer " prefix which is 7 characters)
        final String jwt = authHeader.substring(7);
        final String userEmail = jwtUtil.extractEmail(jwt); // Extract the email from the payload

        // 4. If we found an email and the user isn't already authenticated in this session...
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Check if the user actually exists in the database
            User user = userRepository.findByEmail(userEmail).orElse(null);
            
            // 5. If the user exists and the token is mathematically valid...
            if (user != null && jwtUtil.isTokenValid(jwt, user.getEmail())) {
                
                // Create an authentication object (The official "VIP Pass" for Spring Security)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, new ArrayList<>());
                        
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Hand the VIP pass to Spring Security
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 6. Continue processing the request!
        filterChain.doFilter(request, response);
    }
}