package com.paymybuddy.paymybuddybackend.service;


import com.paymybuddy.paymybuddybackend.model.Token;
import com.paymybuddy.paymybuddybackend.service.impl.TokenServiceImp;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface ITokenService {

    String getToken(HttpServletRequest request, HttpServletResponse response, Authentication authResult,
                    String useremail, TokenServiceImp.TOKEN_TYPE tokenType, TokenServiceImp.DURATION durationType);

    Map<String, String> refreshToken(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, IUserService userService) throws IOException;

    Token findByUserId(Long user);

    Token saveToken(Token token);

    void deleteToken(Token token);
}
