package com.paymybuddy.paymybuddybackend.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Date;

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



