package com.example.tcstest.db_stuff.repository;

import com.example.tcstest.db_stuff.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// In "JpaRepository<Users, Integer>", 'User' refers to the entity class (a.k.a. database table) and 'Integer' refers to the table's primary key type
public interface UsersRepository extends JpaRepository<Users, Integer> {
    // Custom queries:

    @Query(value = "SELECT u FROM Users u WHERE u.email=:userEmail")
    Optional<Users> findByEmail(@Param("userEmail") String userEmail); // Incoming "String userEmail" will be injected into "userEmail" placeholder in the query

}
