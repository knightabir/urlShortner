package com.url.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {

    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    private SecretKey getSignInKey() {
        log.debug("Getting signing key");
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token){
        log.debug("Extracting username from token");
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token){
        log.debug("Extracting expiration date from token");
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    private Claims extractAllClaims(String token){
        log.debug("Parsing token");
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token){
        log.debug("Checking if token is expired");
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username){
        log.debug("Generating token for user: {}", username);
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    private String createToken(Map<String,Object> claims,String subject){
        log.debug("Creating token for user: {}", subject);
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("type","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(getSignInKey())
                .compact();
    }

    public Boolean validateToken (String token){
        log.debug("Validating token");
        return !isTokenExpired(token);
    }

}