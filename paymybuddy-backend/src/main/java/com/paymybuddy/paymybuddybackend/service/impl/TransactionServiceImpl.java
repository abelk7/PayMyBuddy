package com.paymybuddy.paymybuddybackend.service.impl;

import com.paymybuddy.paymybuddybackend.model.Transaction;
import com.paymybuddy.paymybuddybackend.repository.TransactionRepository;
import com.paymybuddy.paymybuddybackend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
