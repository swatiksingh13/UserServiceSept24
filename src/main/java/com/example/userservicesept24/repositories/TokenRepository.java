package com.example.userservicesept24.repositories;

import com.example.userservicesept24.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String tokenValue,
                                                                boolean deleted,
                                                                Date currentTime);
    /*
    select * from tokens
    where value = tokenValue and
    deleted = false and
    expiryAt > currentTime;
     */


    @Override
    Token save(Token token);
    //Update + Insert => Upsert
}
