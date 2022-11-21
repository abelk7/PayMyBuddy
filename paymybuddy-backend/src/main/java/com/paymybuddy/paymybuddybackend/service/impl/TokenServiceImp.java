package com.paymybuddy.paymybuddybackend.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddybackend.model.Token;
import com.paymybuddy.paymybuddybackend.model.User;
import com.paymybuddy.paymybuddybackend.repository.TokenRepository;
import com.paymybuddy.paymybuddybackend.service.ITokenService;
import com.paymybuddy.paymybuddybackend.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service("tokenService")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TokenServiceImp implements ITokenService {

    private Algorithm algorithm;
    private final TokenRepository tokenRepository;

    @Value("${encrypt.header.token}")
    private String encryptHeaderToken;
    @Value("${duration.access.token}")
    private Integer durationAccessToken;
    @Value("${duration.refresh.token}")
    private Integer durationRefreshToken;

    public static enum TOKEN_TYPE {
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }
    public static enum DURATION {
        SEC,
        MIN,
        HOUR
    }

    private Date timeValidToken(Integer value, DURATION duration) {
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

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC512(encryptHeaderToken.getBytes());
    }


    @Override
    public String getToken(HttpServletRequest request, HttpServletResponse response, Authentication authResult,
                           String useremail, TOKEN_TYPE tokenType, DURATION durationType) {
        Date expireAt;
        String tokenGenerated = null;


        switch (tokenType) {
            case ACCESS_TOKEN :
                expireAt = timeValidToken(durationAccessToken, durationType);
                tokenGenerated =  JWT.create()
                        .withSubject(useremail)
                        .withExpiresAt(expireAt)
                        .withIssuer(request.getRequestURI())
                        .sign(algorithm);
                break;
            case REFRESH_TOKEN :
                expireAt = timeValidToken(durationRefreshToken, durationType);
                tokenGenerated = JWT.create()
                        .withSubject(useremail)
                        .withExpiresAt(expireAt)
                        .withIssuer(request.getRequestURI())
                        .sign(algorithm);
                break;
        }

        return tokenGenerated;
    }

    @Override
    public Map<String, String> refreshToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, IUserService userService) throws IOException {
        Map<String, String> contentResponse;
        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        this.algorithm = Algorithm.HMAC512(encryptHeaderToken.getBytes());
        //On récupére leRefreshToken du Header
        String refreshToken = request.getHeader("RefreshToken");
        if(!StringUtils.isEmpty(authorizationHeader)) {
            try{
                contentResponse = new HashMap<>();

                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT1 = verifier.verify(refreshToken);
                String userEmail = decodedJWT1.getSubject();

                //String userEmailFromRequest = request.g;
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, null);

                User user = userService.getUser(userEmail);
                if(user != null) {
                   // vérifier que l'utilisateur correspond bien à l'utilisiateur de token
                   Token tokenRefreshSaved = this.findByUserId(user.getId());
                   if(tokenRefreshSaved != null){
                       DecodedJWT decodedJWT2 = verifier.verify(tokenRefreshSaved.getRefreshToken());

                       String userMailFound = decodedJWT2.getSubject();
                       if(userEmail.equals(userMailFound)){

                           String access_token = getToken(request, response, authenticationToken, userEmail, TOKEN_TYPE.ACCESS_TOKEN, DURATION.MIN);
                           contentResponse.put("access_token", access_token);
                           // generate new refreToken if (si refreshtoken est en fin de vie) dans notre cas si inférieur à 2min
                           if(decodedJWT1.getExpiresAtAsInstant().compareTo(Instant.now().plusSeconds(120)) <= 0){
                               String new_refresh_token = this.getToken(request, response, authenticationToken, userEmail, TOKEN_TYPE.REFRESH_TOKEN, DURATION.MIN);
                               contentResponse.put("refresh_token", new_refresh_token);
                           }else {
                               contentResponse.put("refresh_token", refreshToken);
                           }
                       }
                   }
                }

                return contentResponse;

            } catch (Exception exception) {
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

        return null;
    }

    @Override
    public Token findByUserId(Long userId) {
        List<Token> tokens = tokenRepository.findAll();
        for (Token token: tokens) {
            User user = token.getUser();
            if(userId == user.getId()) {
                return token;
            }
        }
        return null;
    }

    @Override
    public Token saveToken(Token token) {
      return tokenRepository.save(token);
    }

    @Override
    public void deleteToken(Token token){
        tokenRepository.delete(token);
    }
}
