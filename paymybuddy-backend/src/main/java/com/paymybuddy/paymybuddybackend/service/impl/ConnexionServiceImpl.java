package com.paymybuddy.paymybuddybackend.service.impl;

import com.paymybuddy.paymybuddybackend.model.Connexion;
import com.paymybuddy.paymybuddybackend.repository.CompteRepository;
import com.paymybuddy.paymybuddybackend.repository.ConnexionRepository;
import com.paymybuddy.paymybuddybackend.service.ConnexionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ConnexionServiceImpl implements ConnexionService {
    private final ConnexionRepository connexionRepository;

    @Override
    public Connexion saveConnexion(Connexion connexion) {
        return connexionRepository.save(connexion);
    }
}
