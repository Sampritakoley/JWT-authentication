package com.jwtauthentication.authentication_jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AuthenticationJwtApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationJwtApplication.class, args);
	}
    public void run(String... args)
	{
         /* User user=new User();
		  user.setFastName("samprita");
		  user.setLastName("koley");
		  user.setEmail("samprita@gmail.com");
		  user.setRole(Role.Admin);
		  user.setPassword(new BCryptPasswordEncoder().encode("samprita2001"));
		  userRepository.save(user);*/
	}
}
