package com.example.tcstest.oauth2controller;

import com.example.tcstest.WebViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OAuth2Controller {
    private final RestTemplate restTemplate;

    // 'restTemplate' bean is from appConfig.AppConfig
    @Autowired
    public OAuth2Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @GetMapping("/") // Refers to http://localhost:8080/
    public void googleLogin(@AuthenticationPrincipal OAuth2User p) {
        String checkEmail;
        boolean emailExists = false;

        if (p != null) {
            checkEmail = p.getAttribute("email");
            System.out.println("Email: " + checkEmail);

            // Making an HTTP GET request to check if the email exists in the database
            // Making a GET request to the endpoint '/users/checkEmail' with a query parameter named 'email' whose value is the email address retrieved from the OAuth2 authentication.
            // Query parameter starts with '?'
            String url = "http://localhost:8080/users/checkEmail?email=" + checkEmail;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                emailExists = true;
            }
        }

        WebViewController.handleCloseGoogle(emailExists);
    }
}
