package com.liquibase.demo.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    @Value("${app.jwt.access.validity}")
    private long ACCESS_TOKEN_VALIDITY;

    @Value("${app.jwt.refresh.validity}")
    private long REFRESH_TOKEN_VALIDITY;


    public String getTokenFromHeader(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            return jwtToken.substring(7);
        }
        return null;
    }

    public String generateAccessToken(String userName) {
        String accessToken = Jwts.builder().subject(userName).issuedAt(new Date()).expiration(new Date(new Date().getTime() + ACCESS_TOKEN_VALIDITY)).signWith(key())
                .compact();
        return accessToken;
    }


    public String generateRefreshToken(String userName) {
        String refreshToken = Jwts.builder().subject(userName).issuedAt(new Date()).expiration(new Date(new Date().getTime() + REFRESH_TOKEN_VALIDITY)).signWith(key()).compact();

        return refreshToken;
    }



    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }


    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getPayload().getSubject();
    }

    public JWTTokenValidation validateTheTokens(String token) {
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token);
            return JWTTokenValidation.VALID;
        } catch (ExpiredJwtException e) {
            return JWTTokenValidation.EXPIRED;
        } catch (IllegalArgumentException e) {
            return JWTTokenValidation.INVALID;
        }
    }


}
