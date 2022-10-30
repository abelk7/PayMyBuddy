package com.paymybuddy.paymybuddybackend.repository;

import com.paymybuddy.paymybuddybackend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
