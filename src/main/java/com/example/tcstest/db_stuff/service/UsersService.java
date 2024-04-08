package com.example.tcstest.db_stuff.service;

import com.example.tcstest.db_stuff.entity.Users;
import com.example.tcstest.db_stuff.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository userRepository) {
        this.usersRepository = userRepository;
    }

    public Users createUser(Users user) {
        return usersRepository.save(user);
    }

    public Optional<Users> getUserById(int id){
        return usersRepository.findById(id);
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Integer formLoginUser(String userEmail, String userPassword) {
        Optional<Users> userOptional = usersRepository.findByEmail(userEmail);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            if (user.getPassword().equals(userPassword)) {
                // Password matches, login successful
                return user.getId();
            } else {
                // Password doesn't match
                return -1;
            }
        } else {
            // Email doesn't exist
            return -1;
        }
    }

    public boolean existsByEmail(String email){
        Optional<Users> emailExists = usersRepository.findByEmail(email);

        return emailExists.isPresent();
    }

    public Users updateUser(Users user) {
        return usersRepository.save(user);
    }

    public void deleteUser(Integer id) {
        usersRepository.deleteById(id);
    }

}