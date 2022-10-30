package com.paymybuddy.paymybuddybackend.service;

import com.paymybuddy.paymybuddybackend.model.Compte;
import com.paymybuddy.paymybuddybackend.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;

public interface TransactionService {
//    Transaction getTransactionById(Long id);
//    String getEtatTransactionById(Long id);
//    BigDecimal getMontantTransactionById(Long id);
//    Date getDateExecutionTransactionById(Long id);
//    Compte getCompteEmetteurTransactionById(Long id);
//    Compte getCompteBeneficiaireTransactionById(Long id);
    Transaction saveTransaction(Transaction transaction);
}
