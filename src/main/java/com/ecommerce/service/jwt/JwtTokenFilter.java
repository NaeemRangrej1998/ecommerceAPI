package com.ecommerce.service.jwt;

import com.ecommerce.exception.CustomException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.lang.NonNull;

import java.io.IOException;

//todo whole service will refactor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private   HandlerExceptionResolver handlerExceptionResolver;

//    private final JwtService jwtService;

    private  JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(HandlerExceptionResolver handlerExceptionResolver, JwtTokenProvider jwtTokenProvider) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest req,
            @NonNull HttpServletResponse res,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
//        final String authHeader = request.getHeader("Authorization");
//        System.out.println("Request received: "+ authHeader);
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        try {
//            final String jwt = authHeader.substring(7);
//            final String userEmail = jwtService.extractUsername(jwt);
//
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//            if (userEmail != null && authentication == null) {
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//
//                if (jwtService.isTokenValid(jwt, userDetails)) {
//                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails,
//                            null,
//                            userDetails.getAuthorities()
//                    );
//
//                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//                else {
//                    // If token is not valid, return 401
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    return;
//                }
//            }
//
//            filterChain.doFilter(request, response);
//        } catch (Exception exception) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Directly return 401 if any exception occurs
//            return;
////            handlerExceptionResolver.resolveException(request, response, null, exception);
//        }
        try {
            final String authHeader = req.getHeader("Authorization");
            System.out.println("token = " + authHeader);
            String token = jwtTokenProvider.resolveToken(req);
            System.out.println("token = " + token);

            if ((token != null && !token.isEmpty())) {
                try {
                    jwtTokenProvider.isTokenValid(token);
                } catch (JwtException | IllegalArgumentException e) {
                    logger.error("Token expired :::");
                }

                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(req, res);
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
