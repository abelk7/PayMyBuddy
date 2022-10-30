package com.paymybuddy.paymybuddybackend.service;

import com.paymybuddy.paymybuddybackend.model.Compte;
import com.paymybuddy.paymybuddybackend.model.User;

import java.math.BigDecimal;

public interface CompteService {
    Compte saveCompte(Compte compte);
}
