package com.example.authentication.config.SecurityConfig.jwtConfig;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;


@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    @Value("${jwt.secret}")
    private String secret;
    private final long expirationMs = 86400000;


    public String generateJwtToken(UserDetails principal ) {
        Date expire = new Date((new Date()).getTime() + (60000 * expirationMs));
        String token = Jwts.builder()
                .setSubject((principal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return token;
    }

    public String extractUsername(HttpServletRequest request) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(this.parseJwt(request))
                .getBody()
                .getSubject();
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }


}
