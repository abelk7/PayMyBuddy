package com.paymybuddy.paymybuddybackend.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddybackend.Util.TokenManager;
import com.paymybuddy.paymybuddybackend.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Value("${encrypt.token}")
    private String encryptToken;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if( request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")) {//Enleverr code token/refresh

            filterChain.doFilter(request, response);

//            Map<String, String> error = new HashMap<>();
//            error.put("error_message", "Connexion FAILED");
//            response.setContentType("application/json");
//            response.setStatus(404);
//            new ObjectMapper().writeValue(response.getOutputStream(), error);

        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try{
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC512("secretPaymybuddy".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String email = decodedJWT.getSubject();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }catch (TokenExpiredException tokenExpiredException){
                    TokenManager tokenManager = new TokenManager();
                    /*Map<String, String>  result =  */tokenManager.refreshToken(request, response, filterChain);
//                    if(request != null) {
//                        filterChain.doFilter(request, response);
//                        response.setContentType("application/json");
//                        response.setStatus(HTT);
//                    }

                }
                catch (Exception exception) {
                    log.error("Error loggin : {} ", exception.getMessage());
                    response.setHeader("error", exception.getMessage());


                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", exception.getMessage());
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
