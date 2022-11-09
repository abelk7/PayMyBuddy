package com.paymybuddy.paymybuddybackend.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddybackend.model.User;
import com.paymybuddy.paymybuddybackend.service.UserService;
import com.paymybuddy.paymybuddybackend.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
public class TokenManager {

    private Algorithm algorithm;


    public enum DURATION {
        SEC,
        MIN,
        HOUR
    }

    public TokenManager(Algorithm algorithm) {
        this.algorithm = algorithm;
    }



    public String getToken(HttpServletRequest request, HttpServletResponse response, Authentication authResult,
                                 String useremail, Date expireAt) {
        return JWT.create()
                .withSubject(useremail)
                .withExpiresAt(expireAt)
                .withIssuer(request.getRequestURI())
                .sign(algorithm);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response,  FilterChain filterChain) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String refreshToken = request.getHeader("RefreshToken");
        if(!StringUtils.isEmpty(refreshToken)) {
            try{
                //String refresh_token = authorizationHeader.substring("Bearer ".length());
                //Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String userEmail = decodedJWT.getSubject();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, null);

                String access_token = getToken(request, response, authenticationToken, userEmail, timeValidToken(0, DURATION.MIN));

                Map<String, String> result = new HashMap<>();
                result.put("access_token", access_token);


                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                response.setContentType("application/json");
//                response.setStatus(401);
                new ObjectMapper().writeValue(response.getOutputStream(), result);
                filterChain.doFilter(request, response);

//                response.setContentType("application/json");
//                new ObjectMapper().writeValue(response.getOutputStream(), result);;
            }catch (TokenExpiredException tokenExpiredException) {
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", "Vous devez vous connecter à nouveau sur l'application");
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
            catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                error.put("error_app", "1");
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    public Date timeValidToken(Integer value, DURATION duration) {
        Date date;
        switch (duration) {
            case SEC:
                date = new Date(System.currentTimeMillis() + value * 1000);
                break;
            case MIN:
            default:
                date = new Date(System.currentTimeMillis() + value * 60 * 1000);
                break;
            case HOUR:
                date = new Date(System.currentTimeMillis() + value  * 60 * 60 * 1000);
                break;
        }
        return date;
    }
}



