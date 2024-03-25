package com.example.tcstest.oauth2controller;

import com.example.tcstest.WebViewController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class OAuth2Controller {

    @GetMapping("/")
    public Principal googleLogin(Principal p) {
        WebViewController.handleCloseGoogle();
        return p;
    }
}
