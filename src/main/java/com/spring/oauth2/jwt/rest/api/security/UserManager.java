package com.spring.oauth2.jwt.rest.api.security;

import com.spring.oauth2.jwt.rest.api.model.User;
import com.spring.oauth2.jwt.rest.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class UserManager implements UserDetailsManager {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails user) {
        ((User) user).setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save((User) user);
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        User existingUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with username {0} not found", userDetails.getUsername())));
        existingUser.setUsername(((User) existingUser).getUsername());
        existingUser.setPassword(passwordEncoder.encode(((User) existingUser).getPassword()));
        userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with username {0} not found", username)));
        userRepository.delete(user);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        String username = getCurrentAuthenticatedUsername();
        User user = (User) loadUserByUsername(username);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private String getCurrentAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with username {0} not found", username)));
    }

} 
