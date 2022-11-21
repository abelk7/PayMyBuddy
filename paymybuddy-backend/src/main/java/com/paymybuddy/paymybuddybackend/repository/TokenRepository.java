package com.paymybuddy.paymybuddybackend.repository;

import com.paymybuddy.paymybuddybackend.model.Token;
import com.paymybuddy.paymybuddybackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
//    Token findByUser(User user);
}

