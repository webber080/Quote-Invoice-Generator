package com.example.tcstest.oauth2controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OAuth2Controller {
    @GetMapping
    public ResponseEntity<String> response() {
        return ResponseEntity.ok("This is a response!");
    }
}
