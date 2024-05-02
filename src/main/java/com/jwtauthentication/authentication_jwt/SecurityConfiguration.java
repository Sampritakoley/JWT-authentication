package com.jwtauthentication.authentication_jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Autowired
    private  JwtAuthFilter jwtAuthFilter;
    @Autowired
    private  UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws  Exception{
          http.csrf(AbstractHttpConfigurer::disable)
                  .authorizeHttpRequests(request->request.requestMatchers("/api/v1/auth/**").permitAll()
                          .requestMatchers("/api/v1/admin/**").hasRole(Role.Admin.name())
                          .requestMatchers("/api/v1/user/**").hasRole(Role.User.name())
                          .anyRequest().authenticated())
                  .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                  .authenticationProvider(authenticationProvider()).addFilterBefore(
                          jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
                  );
          return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService.userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception{
        return auth.getAuthenticationManager();
    }
}
