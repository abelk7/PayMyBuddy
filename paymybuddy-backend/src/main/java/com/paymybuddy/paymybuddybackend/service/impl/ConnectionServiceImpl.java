package com.paymybuddy.paymybuddybackend.service.impl;

import com.paymybuddy.paymybuddybackend.exception.UserNotFoundException;
import com.paymybuddy.paymybuddybackend.model.Connection;
import com.paymybuddy.paymybuddybackend.repository.ConnectionRepository;
import com.paymybuddy.paymybuddybackend.service.IConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ConnectionServiceImpl implements IConnectionService {

    private final ConnectionRepository connectionRepository;

//    @Override
//    public Connection create(Connection connection) throws Exception {
//        if(connection.getSenderUser() != null && connection.getRecipientUser() != null) {
//            connection.setStatus("En attente");
//            return connectionRepository.save(connection);
//        }else {
//            throw new Exception();
//        }
//    }

    @Override
    public Connection create(Connection connection) {
        if(connection.getSenderUser() != null && connection.getRecipientUser() != null) {
            connection.setStatus("En attente");
            return connectionRepository.save(connection);
        }else {
            throw new UserNotFoundException("Un des utilisateurs ne satisfait pas la requête pour que la connection soit créee.");
        }
    }

//    @Override
//    public Connection saveConnection(Connection connection) {
//        return connectionRepository.save(connection);
//    }
}
