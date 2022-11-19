package com.paymybuddy.paymybuddybackend;

import com.paymybuddy.paymybuddybackend.exception.UserNotFoundException;
import com.paymybuddy.paymybuddybackend.model.Connection;
import com.paymybuddy.paymybuddybackend.model.User;
import com.paymybuddy.paymybuddybackend.repository.ConnectionRepository;
import com.paymybuddy.paymybuddybackend.service.impl.ConnectionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ConnectionServiceTest {

    @Mock
    private ConnectionRepository connectionRepository;


    private ConnectionServiceImpl connectionService;

    List<Connection> connectionList;

    @BeforeEach
    private void setUpTest() {
        connectionService = new ConnectionServiceImpl(connectionRepository);

        connectionList = new ArrayList<>();

        //init users
        User user1 = new User();
        user1.setId(1L);
        user1.setPrenom("Jacob");
        user1.setNom("Miller");
        user1.setEmail("j.miller@gmail.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setPrenom("Melusine");
        user2.setNom("Potler");
        user2.setEmail("m.potler@gmail.com");


        User user3 = new User();
        user3.setId(3L);
        user3.setPrenom("Jack");
        user3.setNom("Parkson");
        user3.setEmail("j.parkson@gmail.com");


        User user4 = new User();
        user4.setId(4L);
        user4.setPrenom("Felix");
        user4.setNom("Dumler");
        user4.setEmail("f.dumler@gmail.com");

        //Init connections
        Connection connection1 = new Connection();
        connection1.setId(1L);
        connection1.setSenderUser(user1);
        connection1.setRecipientUser(user2);
        connectionList.add(connection1);

        Connection connection2 = new Connection();
        connection2.setId(2L);
        connection2.setSenderUser(user1);
        connection2.setRecipientUser(null);
        connectionList.add(connection2);

        Connection connection3 = new Connection();
        connection3.setId(3L);
        connection3.setSenderUser(user1);
        connection3.setRecipientUser(user4);
        connectionList.add(connection3);

        Connection connection4 = new Connection();
        connection4.setId(4L);
        connection4.setSenderUser(user2);
        connection4.setRecipientUser(user3);
        connectionList.add(connection4);

        Connection connection5 = new Connection();
        connection5.setId(5L);
        connection5.setSenderUser(user2);
        connection5.setRecipientUser(user4);
        connectionList.add(connection5);

        Connection connection6 = new Connection();
        connection6.setId(6L);
        connection6.setSenderUser(user3);
        connection6.setRecipientUser(user4);
        connectionList.add(connection6);

    }

    @Test
    void createConnection_shouldAddNewConnection() {

        Connection connection = connectionList.get(0);

        when(connectionRepository.save(any(Connection.class))).thenReturn(connection);

        Connection connectionCreated = connectionService.create(connection);

        assertThat(connectionCreated).isNotNull();
    }

    @Test
    void createConnection_shouldNotAddNewConnection() {
        // when RecipientUser is null
        Connection connection = connectionList.get(1);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            connectionService.create(connection);
        });

        assertThat(exception.getMessage()).isEqualTo("Un des utilisateurs ne satisfait pas la requête pour que la connection soit créee.");
    }

}
