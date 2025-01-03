package com.socket.chatzalo.config.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateTokenFromUsername(UserDetails userDetails) {
        String username = userDetails.getUsername();
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + jwtExpirationMs;

        return Jwts.builder()
                .claim("sub", username) // Thêm username
                .claim("iat", nowMillis / 1000)
                .claim("exp", expMillis / 1000)
                .signWith(key()) // Chữ ký
                .compact();
    }



    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("JWT validation failed: {}", e.getMessage());
        }
        return false;
    }
}
