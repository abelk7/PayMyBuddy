package com.paymybuddy.paymybuddybackend.repository;

import com.paymybuddy.paymybuddybackend.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    Compte findByUserId(Long id);
}
