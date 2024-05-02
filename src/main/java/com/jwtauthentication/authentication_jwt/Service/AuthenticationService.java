package com.jwtauthentication.authentication_jwt.Service;

import com.jwtauthentication.authentication_jwt.*;

public interface AuthenticationService {
    public User signUp(RegisterDto registerDto);

    public TokenDto sigin(SigninRequestDto signinRequestDto);

    public TokenDto requestRefreshToken(RequestRefreshToken requestRefreshToken);
}
