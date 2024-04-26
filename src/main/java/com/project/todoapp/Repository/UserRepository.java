package com.project.todoapp.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.todoapp.Models.User;
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
