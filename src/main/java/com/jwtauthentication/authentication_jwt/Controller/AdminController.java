package com.jwtauthentication.authentication_jwt.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping("/welcome")
    public String GetWelcomeMsg()
    {
        return "Welcome admin";
    }
}
