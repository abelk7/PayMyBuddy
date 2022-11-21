package com.paymybuddy.paymybuddybackend.service;

import com.paymybuddy.paymybuddybackend.model.Transaction;

public interface ITransactionService {
//    Transaction getTransactionById(Long id);
//    String getEtatTransactionById(Long id);
//    BigDecimal getMontantTransactionById(Long id);
//    Date getDateExecutionTransactionById(Long id);
//    Compte getCompteEmetteurTransactionById(Long id);
//    Compte getCompteBeneficiaireTransactionById(Long id);
    Transaction saveTransaction(Transaction transaction);
}
