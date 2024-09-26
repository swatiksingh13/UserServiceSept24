package com.example.userservicesept24.services;

import com.example.userservicesept24.models.Token;
import com.example.userservicesept24.models.User;
import com.example.userservicesept24.repositories.TokenRepository;
import com.example.userservicesept24.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User with email: " + email + " not found in the DB.");
        }

        User user = optionalUser.get();

        if (bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            //Generate the token.
            Token token = createToken(user);
            Token savedToken = tokenRepository.save(token);


            return savedToken;
        }

        return null;
    }

    public User signUp(String name,
                       String email,
                       String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);

        //First encrypt the password using BCrypt Algorithm before storing into the DB.
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }

    public void logout(String tokenValue) {

    }

    public User validateToken(String tokenValue) {
        return null;
    }

    private Token createToken(User user) {
        Token token = new Token();
        token.setUser(user);

        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        //Expiry time of the token is let's say 30 days from now.
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysAfterCurrentTime = today.plusDays(30);

        Date expiryAt = Date.from(thirtyDaysAfterCurrentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
        token.setExpiryAt(expiryAt);

        return token;
    }
}
