package com.paymybuddy.paymybuddybackend.repository;

import com.paymybuddy.paymybuddybackend.model.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeTransactionRepository extends JpaRepository<TypeTransaction, Long> {
    
}
