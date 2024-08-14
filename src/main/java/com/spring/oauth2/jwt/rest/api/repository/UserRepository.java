package com.spring.oauth2.jwt.rest.api.repository;

import java.util.Optional;

import com.spring.oauth2.jwt.rest.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
} 
