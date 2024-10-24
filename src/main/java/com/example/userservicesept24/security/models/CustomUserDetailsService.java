package com.example.userservicesept24.security.models;

import com.example.userservicesept24.UserRepository;
import com.example.userservicesept24.models.User;
import jakarta.ws.rs.OPTIONS;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService  {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + username + " not found.");
        }

        User user = optionalUser.get();

        //Convert user object to type of UserDetails object.

        return null;
    }
}
