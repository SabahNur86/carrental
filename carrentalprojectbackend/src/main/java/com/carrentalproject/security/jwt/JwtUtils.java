package com.carrentalproject.security.jwt;

import com.carrentalproject.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${backendapi.app.jwtSecret}")
    private String jwtSecret;

    @Value("${backendapi.app.jwtExpirationMS}")
    private long jwtExpirationMS;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder().setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime()+jwtExpirationMS))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
                .getBody().getSubject();

    }

    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e){
            logger.error("Invalid Jwt signature :{}", e.getMessage());
        }catch (MalformedJwtException e){
            logger.error("Invalid Jwt token :{}", e.getMessage());
        }catch (ExpiredJwtException e){
            logger.error("Jwt token is expired :{}", e.getMessage());
        }catch (UnsupportedJwtException e){
            logger.error("Jwt token is unsupported :{}", e.getMessage());
        }catch (IllegalArgumentException e){
            logger.error("Jwt claims is empty :{}", e.getMessage());
        }
        return false;
    }
}