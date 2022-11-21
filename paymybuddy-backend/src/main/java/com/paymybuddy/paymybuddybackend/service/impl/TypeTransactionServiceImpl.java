package com.paymybuddy.paymybuddybackend.service.impl;

import com.paymybuddy.paymybuddybackend.model.TypeTransaction;
import com.paymybuddy.paymybuddybackend.repository.TypeTransactionRepository;
import com.paymybuddy.paymybuddybackend.service.ITypeTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TypeTransactionServiceImpl implements ITypeTransactionService {
    private final TypeTransactionRepository typeTransactionRepository;

    @Override
    public TypeTransaction saveTypeTransaction(TypeTransaction typeTransaction) {
        return typeTransactionRepository.save(typeTransaction);
    }
}
