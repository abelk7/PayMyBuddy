package com.paymybuddy.paymybuddybackend.service.impl;


import com.paymybuddy.paymybuddybackend.model.Compte;
import com.paymybuddy.paymybuddybackend.repository.CompteRepository;
import com.paymybuddy.paymybuddybackend.service.ICompteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CompteServiceImpl implements ICompteService {
    private final CompteRepository compteRepository;

    @Override
    public Compte saveCompte(Compte compte) {
        return compteRepository.save(compte);
    }
}
