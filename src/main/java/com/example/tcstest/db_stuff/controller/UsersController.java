package com.example.tcstest.db_stuff.controller;

import com.example.tcstest.db_stuff.entity.Users;
import com.example.tcstest.db_stuff.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users") // Base URL path (a.k.a. http://localhost:8080/users). Any paths below will be appended to this path
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }


    @PostMapping // Refers to http://localhost:8080/users/
    public Users createUser(@RequestBody Users user) { // '@RequestBody' takes the content of the body sent to this path
        return usersService.createUser(user);
    }

    @GetMapping("/{id}") // Refers to http://localhost:8080/users/{id}
    public Users getUserById(@PathVariable int id) {
        Optional<Users> user = usersService.getUserById(id);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }

    @GetMapping // Refers to http://localhost:8080/users/
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    @PostMapping("/formLogin") // Refers to http://localhost:8080/users/formLogin
    public Integer formLoginUser(@RequestParam String email, @RequestParam String password) {
        return usersService.formLoginUser(email, password);
    }

    // Called from oauth2controller.OAuth2Controller.java
    @GetMapping("/checkEmail") // Refers to http://localhost:8080/users/checkEmail
    public ResponseEntity<String> checkEmailExists(@RequestParam String email) {
        // Perform database query to check if email exists
        boolean emailExists = usersService.existsByEmail(email);

        if (emailExists) {
            return ResponseEntity.ok("Email exists in the database.");
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Email does not exist in the database.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users user) {
        user.setId(id);
        return ResponseEntity.ok(usersService.updateUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}

