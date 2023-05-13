package com.coelho.fazfeira.config;


import com.coelho.fazfeira.dto.TokenDto;
import com.coelho.fazfeira.dto.UserDto;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class JwtGeneratorImpl implements JwtGenerator {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${app.jwttoken.message}")
    private String message;

    @Override
    public TokenDto generateToken(UserDto user) {
        final LocalDateTime localDateTime = LocalDateTime.now().plusHours(3);
        // Date expireDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        String jwtToken = "";

        String payload = new Gson().toJson(user);
        jwtToken = Jwts.builder()
                .setPayload(payload)
//                .setSubject(user.getId().toString())
                //.setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secret")
                //.setExpiration(expireDate)
                .compact();
        return TokenDto.builder()
                .token(jwtToken)
                .message(message)
                .expiredAt(localDateTime.format(DateTimeFormatter
                        .ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
