package com.jwtauthentication.authentication_jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JwtServiceImp implements  JwtService{
    public String generateToken(UserDetails userDetail)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetail.getAuthorities());
        return Jwts.builder()
                .setSubject(userDetail.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 *24))
                .signWith(getSighKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateRefreshToken(HashMap<String,Object> extraClaims,UserDetails userDetail){
       // extraClaims.put("role", userDetail.getAuthorities());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetail.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 604800000))
                .signWith(getSighKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolvers){
        final Claims claims=extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Key getSighKey()
    {
        byte[] key= Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder().setSigningKey(getSighKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String userName=extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }
}
