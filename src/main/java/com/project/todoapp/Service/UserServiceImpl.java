package com.project.todoapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.project.todoapp.Models.User;
import com.project.todoapp.Repository.UserRepository;

import jakarta.validation.Valid;
@Validated
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public User registerUser(@Valid User user) {
        return userRepository.save(user);
    }
    
}
