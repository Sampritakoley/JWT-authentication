package com.jwtauthentication.authentication_jwt.Service;

import com.jwtauthentication.authentication_jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService
{
    @Autowired
    private  UserRepository userRepositotory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public User signUp(RegisterDto registerDto)
    {
        User user=new User();
        user.setEmail(registerDto.getEmail());
        user.setFastName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setRole(Role.User);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        return userRepositotory.save(user);
    }

    @Override
    public TokenDto sigin(SigninRequestDto signinRequestDto){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequestDto.getUsername(),
                signinRequestDto.getPassword()));

        var newuser=userRepositotory.findByEmail(signinRequestDto.getUsername()).orElseThrow(()->new IllegalArgumentException("User is invalid"));

        var accessToken=jwtService.generateToken(newuser);

        var refreshToken=jwtService.generateRefreshToken(new HashMap<>(),newuser);
        TokenDto tokenDto=new TokenDto();
        tokenDto.setAccessToken(accessToken);
        tokenDto.setRefreshToken(refreshToken);
        return tokenDto;
    }

    public TokenDto requestRefreshToken(RequestRefreshToken requestRefreshToken)
    {
        String userEmail=jwtService.extractUserName(requestRefreshToken.getToken());
        Optional<User> userOp=userRepositotory.findByEmail(userEmail);
        User user=userOp.get();
        if(jwtService.isTokenValid(requestRefreshToken.getToken(),user)){
            var jwt=jwtService.generateToken(user);
            TokenDto tokenDto=new TokenDto();
            tokenDto.setRefreshToken(requestRefreshToken.getToken());
            tokenDto.setAccessToken(jwt);
            return tokenDto;
        }
        return null;
    }
}
