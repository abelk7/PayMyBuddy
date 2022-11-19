package com.paymybuddy.paymybuddybackend.repository;

import com.paymybuddy.paymybuddybackend.model.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    Connection save(Connection connection);
}
