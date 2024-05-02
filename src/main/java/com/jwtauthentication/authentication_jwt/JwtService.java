package com.jwtauthentication.authentication_jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;


public interface JwtService {
    public String generateToken(UserDetails userDetail);
    public String extractUserName(String token);

    public boolean isTokenValid(String token,UserDetails userDetails);
    public String generateRefreshToken(HashMap<String,Object> extraClaims, UserDetails userDetail);

}
