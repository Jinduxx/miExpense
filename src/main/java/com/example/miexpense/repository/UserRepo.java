package com.example.miexpense.repository;

import com.example.miexpense.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    User findPersonByEmail(String email);
    User findUserById(Long id);
    User deleteUserByEmail(String email);
}
