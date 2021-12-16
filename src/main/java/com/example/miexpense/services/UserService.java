package com.example.miexpense.services;

import com.example.miexpense.model.User;

public interface UserService {
    boolean createUser(User user);
    User getUser(String email, String password);
    User deleteUser(String email);
    User findUser(Long userId);
}
