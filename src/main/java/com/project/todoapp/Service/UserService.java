package com.project.todoapp.Service;

import com.project.todoapp.Models.User;

import jakarta.validation.Valid;

public interface UserService {
    User findByUsername(String username);
    User registerUser(@Valid User user);
}
