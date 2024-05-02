package com.jwtauthentication.authentication_jwt;


;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImp implements  UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetailsService userDetailsService(){
            return new UserDetailsService() {
                @Override
                public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    User user = userRepository.findByEmail(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

                    return org.springframework.security.core.userdetails.User.builder()
                            .username(user.getEmail())
                            .password(user.getPassword())
                            .roles(user.getRole().name()) // Assuming you store roles as strings in the database
                            .build();
                }
            };
    }
}
