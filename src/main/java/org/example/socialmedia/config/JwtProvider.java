package org.example.socialmedia.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${spring.application.my_token.key}")
    String key;
    @Value("${spring.application.my_token.expire_time}")
    Long expireTime;

    public String generateToken(UserDetails userDetails) {
        Date date = new Date(System.currentTimeMillis()+expireTime);
        return Jwts
                .builder()
                .setIssuedAt(new Date())
                .setSubject(userDetails.getUsername())
                .setExpiration(date)
                .signWith(signKey(), SignatureAlgorithm.HS256)
                .compact();
    }



    public Key signKey() {
       return Keys.hmacShaKeyFor(key.getBytes());
    }

    public String getSubject(String auth) {
        String subject ="";
        try {
            subject = Jwts
                    .parserBuilder()
                    .setSigningKey(signKey())
                    .build()
                    .parseClaimsJws(auth)
                    .getBody()
                    .getSubject()
                    .toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return subject;
    }
}



