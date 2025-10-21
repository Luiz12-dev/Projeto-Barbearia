package br.com.atividade.barbearia.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

   public static final  Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${barbearia.app.jwtSecret}")
   private String jwtSecret;

   @Value("${barbearia.app.jwtExpirationMs}")
   private int jwtExpiration;

   @Value("${barbearia.app.jwtRefreshSecret}")
   private String jwtRefreshSecret;

   @Value("${barbearia.app.jwtRefreshExpirationMs}")
   private int jwtRefreshExpirationMs;

   private Key getSignedKey(String secret) {
       return Keys.hmacShaKeyFor(secret.getBytes());
   }


   public String generateJwtToken(Authentication authentication){

       UserDetails userDetails = (UserDetails) authentication.getPrincipal();

       List<String> roles = userDetails.getAuthorities()
               .stream()
               .map(GrantedAuthority::getAuthority)
               .collect(Collectors.toList());

       return Jwts.builder()
               .setSubject(userDetails.getUsername())
               .claim("roles", roles)
               .setIssuedAt(new Date())
               .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
               .signWith(getSignedKey(jwtSecret), SignatureAlgorithm.HS512)
               .compact();
   }

   public String generateRefreshToken(Authentication authentication){

       UserDetails userDetails = (UserDetails) authentication.getPrincipal();

       return Jwts.builder()
               .setSubject((userDetails.getUsername()))
               .setIssuedAt(new Date())
               .setExpiration(new Date((new Date()).getTime() + jwtRefreshExpirationMs))
               .signWith(getSignedKey(jwtRefreshSecret), SignatureAlgorithm.HS512)
               .compact();
   }

   public String getUsernameFromJwtToken(String token){
       return Jwts.parserBuilder()
               .setSigningKey(getSignedKey(jwtSecret))
               .build()
               .parseClaimsJws(token).getBody().getSubject();
   }

   public String getUsernameFromRefreshToken(String refreshToken){
       return Jwts.parserBuilder()
               .setSigningKey(getSignedKey(jwtRefreshSecret))
               .build()
               .parseClaimsJws(refreshToken).getBody().getSubject();
   }

   public boolean validateJwtToken(String authToken){
       try{
           Jwts.parserBuilder()
                   .setSigningKey(getSignedKey(jwtSecret))
                   .build()
                   .parseClaimsJws(authToken);
           return true;
       }catch (ExpiredJwtException e){
           logger.error("JWT Token Expired {}", e.getMessage());
       }catch (MalformedJwtException e){
           logger.error("JWT Token Malformed {}", e.getMessage());
       }catch (Exception e){
           logger.error("JWT Token Exception {}", e.getMessage());
       }
       return false;
   }
   public boolean validateRefreshToken(String refreshToken){
       try{
           Jwts.parserBuilder()
                   .setSigningKey(getSignedKey(jwtRefreshSecret))
                   .build()
                   .parseClaimsJws(refreshToken);
           return true;
       }catch (ExpiredJwtException e){
           logger.error("JWT Refreshtoken expired: {} ", e.getMessage());
       }catch (MalformedJwtException e){
           logger.error("JWT Refreshtoken malformed : {} ", e.getMessage());
       }catch (Exception e){
           logger.error("JWT Refreshtoken validation failed: {} ", e.getMessage());
       }
       return false;
   }

    private Claims getClaimsFromToken(String token, String secret) {
            return Jwts.parserBuilder().setSigningKey(getSignedKey(secret)).build().parseClaimsJws(token).getBody();
    }


    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, jwtSecret).getExpiration();
    }

}
