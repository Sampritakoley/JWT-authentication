package com.jwtauthentication.authentication_jwt.Controller;

import com.jwtauthentication.authentication_jwt.*;
import com.jwtauthentication.authentication_jwt.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public String signup(@RequestBody RegisterDto registerDto)
    {
            User user=authenticationService.signUp(registerDto);
            return "User is registered Successfully";
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDto> signin(@RequestBody SigninRequestDto signinRequestDto)
    {
        TokenDto tokendto=authenticationService.sigin(signinRequestDto);
        return  ResponseEntity.ok(tokendto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto> refreshToken(@RequestBody RequestRefreshToken requestRefreshToken){
        TokenDto tokenDto=authenticationService.requestRefreshToken(requestRefreshToken);
        return ResponseEntity.ok(tokenDto);
    }

}
