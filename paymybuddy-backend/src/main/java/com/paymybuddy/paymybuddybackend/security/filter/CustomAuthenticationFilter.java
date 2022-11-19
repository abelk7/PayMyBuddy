package com.paymybuddy.paymybuddybackend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.paymybuddybackend.Util.TokenManager;
import com.paymybuddy.paymybuddybackend.Util.TokenManager.TOKEN_TYPE;
import com.paymybuddy.paymybuddybackend.Util.TokenManager.DURATION;
import com.paymybuddy.paymybuddybackend.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenService;

    @Override
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = request.getParameter("email");
        String test = request.getHeader("email");
        String password = request.getParameter("password");



        log.info("Try login with Email:{} & Password: {}",email, password);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
//        TokenManager tokenManager = new TokenManager(Algorithm.HMAC512(tokenManager.getEncryptToken().getBytes()));
        TokenManager tokenManager1 = new TokenManager();
        User userDetail = (User) auth.getPrincipal();
        Map<String, String> content = new HashMap<>();

        String access_token = tokenService.getToken(request, response, auth, userDetail.getUsername(), TOKEN_TYPE.ACCESS_TOKEN,DURATION.MIN);


        String refresh_token = tokenService.getToken(request, response, auth, userDetail.getUsername(), TOKEN_TYPE.REFRESH_TOKEN, DURATION.MIN);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        content.put("access_token", access_token);
        content.put("refresh_token", refresh_token);
        content.put("userEmail", userDetail.getUsername());

        new ObjectMapper().writeValue(response.getOutputStream(), content);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> content = new HashMap<>();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        if (failed.getCause() instanceof UserNotFoundException) {
            //response.sendError((HttpServletResponse.SC_UNAUTHORIZED), failed.getMessage());
            content.put("message", failed.getMessage());
        }else {
            content.put("message", "Mot de passe incorrect.");
        }
        content.put("error", String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
        new ObjectMapper().writeValue(response.getOutputStream(), content);
    }
}
