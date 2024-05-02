package com.jwtauthentication.authentication_jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
     UserDetailsService userDetailsService();
}
