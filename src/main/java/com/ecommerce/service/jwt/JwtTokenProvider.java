package com.ecommerce.service.jwt;

import com.ecommerce.dto.response.JwtResponseDto;
import com.ecommerce.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Autowired
    private UserRepository userRepository;

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("bearerToken = " + bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        if (bearerToken != null) {
            return bearerToken;
        }
        return bearerToken;
    }


    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())  // Make sure you use the correct method to get the key
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token is invalid
            return false;
        }
    }
    public String getUsername(String token) {
        return Jwts.parserBuilder()  // Use parserBuilder() instead of parser()
                .setSigningKey(getSignInKey())  // Use getSignInKey() to get the signing key
                .build()  // Build the parser
                .parseClaimsJws(token)  // Parse the token
                .getBody()  // Extract the body of the JWT
                .getSubject();  // Get the subject (typically the username)
    }

    public JwtResponseDto createAccessToken(String username)  {

        Claims claims = Jwts.claims().setSubject(username);
        String token= Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) //todo research hs256 and standard expirationtime
                .compact();

        return new JwtResponseDto(token); // Return the token wrapped in JwtResponseDto

    }
    public Authentication getAuthentication(String token) throws JsonProcessingException {
        final String userEmail = getUsername(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
