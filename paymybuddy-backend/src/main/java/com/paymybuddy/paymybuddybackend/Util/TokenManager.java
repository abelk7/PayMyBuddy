package com.paymybuddy.paymybuddybackend.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddybackend.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;



//@Service
//@Transactional
@Slf4j
@Service("tokenService")
public class TokenManager {


    private Algorithm algorithm;

//    @Autowired
//    Environment environment;

    private String encryptHeaderToken = "secretPaymybuddy";

    private Integer durationAccessToken = 0;

    private Integer durationRefreshToken = 30;

    public enum TOKEN_TYPE {
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }

    public enum DURATION {
        SEC,
        MIN,
        HOUR
    }

//    public TokenManager() {//        //this.algorithm = Algorithm.HMAC512(encryptToken.getBytes());
////
////      /*  this.algorithm = Algorithm.HMAC512(environment.getProperty("encrypt.token").getBytes());
////        String rst = environment.getProperty("encrypt.token");
////        System.out.println("RSLT : " + rst);*/
//        this.algorithm = Algorithm.HMAC512(encryptToken.getBytes());
//        //userService =(IUserService) appContext.getBean("userService");
//
//    }



//    public TokenManager(Algorithm algorithm) {
//        this.algorithm = algorithm;
//    }

    public TokenManager(){
        this.algorithm = Algorithm.HMAC512(encryptHeaderToken.getBytes());
    }

    @PostConstruct
    private void init() {

    }



    public String getToken(HttpServletRequest request, HttpServletResponse response, Authentication authResult,
                                 String useremail,TOKEN_TYPE tokenType ,DURATION durationType) {




//        encryptToken = environment.getProperty("encrypt.token");
//        durationAccessToken = Integer.parseInt(environment.getProperty("duration.access.token"));
//        durationRefreshToken = Integer.parseInt(environment.getProperty("duration.refresh.token"));
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

    public void refreshToken(HttpServletRequest request, HttpServletResponse response,  FilterChain filterChain) throws IOException {
        Map<String, String> contentResponse ;
         String authorizationHeader = request.getHeader(AUTHORIZATION);
         //On récupére leRefreshToken du Header
        String refreshToken = request.getHeader("RefreshToken");
        if(!StringUtils.isEmpty(authorizationHeader)) {
            try{
                contentResponse = new HashMap<>();

//                String token = authorizationHeader.substring("Bearer ".length());
//                Algorithm algorithm2 = Algorithm.HMAC256(encryptToken.getBytes());

                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String userEmail = decodedJWT.getSubject();


                //String userEmailFromRequest = request.g;
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail, null);

                //TODO 1 : TEST usermail = usermail from request
                User userDetail = (User) authenticationToken.getPrincipal();
                if(userEmail == userDetail.getUsername()){

                    //TODO 2: vérifier que le refreshtoken correspond bien à l'utilisiateur
                    com.paymybuddy.paymybuddybackend.model.User user = userService.getUser(userEmail);
                    if(user != null) {

                        String access_token = getToken(request, response, authenticationToken, userEmail, TOKEN_TYPE.ACCESS_TOKEN, DURATION.MIN);
                        contentResponse.put("access_token", access_token);

                        //TODO 3 : generate new refreToken if (si refreshtoken est en fin de vie) dans notre cas sin iférieur à 2min
                        if(decodedJWT.getExpiresAtAsInstant().compareTo(Instant.now().plusSeconds(120)) <= 0){
                            String new_refresh_token = getToken(request, response, authenticationToken, userEmail, TOKEN_TYPE.REFRESH_TOKEN, DURATION.MIN);
                            contentResponse.put("refresh_token", new_refresh_token);
                        }else {
                            contentResponse.put("refresh_token", refreshToken);
                        }


                    }

                }

//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                response.setContentType("application/json");
//                response.setStatus(401);
//                new ObjectMapper().writeValue(response.getOutputStream(), content);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(contentResponse.toString());

                filterChain.doFilter(request, response);

//                response.setContentType("application/json");
//                new ObjectMapper().writeValue(response.getOutputStream(), result);;
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

//        String token = authorizationHeader.substring("Bearer ".length());
//
//        try{
//            JWTVerifier verifier = JWT.require(algorithm).build();
//            DecodedJWT decodedJWT = verifier.verify(token);
//        }
//
//
//        TokenManager tokenManager = new TokenManager(Algorithm.HMAC512("secret".getBytes()));
//        org.springframework.security.core.userdetails.User userDetail = (User) auth.getPrincipal();
//        Map<String, String> content = new HashMap<>();
//
//        String access_token = tokenManager.getToken(request, response, auth, userDetail.getUsername(),
//                tokenManager.timeValidToken(0, DURATION.MIN));

//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setContentType("application/json");
//        content.put("access_token", access_token);
////        content.put("refresh_token", refresh_token);
//
//        new ObjectMapper().writeValue(response.getOutputStream(), content);
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
}



