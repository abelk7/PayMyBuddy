package com.paymybuddy.paymybuddybackend.controller;

import com.paymybuddy.paymybuddybackend.model.Connection;
import com.paymybuddy.paymybuddybackend.model.User;
import com.paymybuddy.paymybuddybackend.service.IConnectionService;
import com.paymybuddy.paymybuddybackend.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConnectionController {
    private final IConnectionService connetionService;
    private final IUserService userService;
    private final IConnectionService connectionService;

//    private final UserDetailsService userDetailsService;

    @PostMapping("/addConnection")
    @ResponseBody
    public ResponseEntity<JSONObject> addConnection(@RequestParam String emailSender, @RequestParam String emailRecipient) {
        URI location = null;
        boolean created = false;
        User senderUser = userService.getUser(emailSender);
        User recipientUser = userService.getUser(emailRecipient);

        if(senderUser != null && recipientUser != null) {
            Connection connection = new Connection();
            connection.setSenderUser(senderUser);
            connection.setRecipientUser(recipientUser);

            if(connetionService.create(connection) != null) {
                location = URI.create("/connetions");
                created = true;
            }
        }

        if(created) {
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.badRequest().build();
    }
}
