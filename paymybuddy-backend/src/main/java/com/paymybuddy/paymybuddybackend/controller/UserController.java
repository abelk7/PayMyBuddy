package com.paymybuddy.paymybuddybackend.controller;

import com.paymybuddy.paymybuddybackend.model.User;
import com.paymybuddy.paymybuddybackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @PostMapping("/login")
//    public ResponseEntity<?> saveUser(@RequestBody User user) {
//        return ResponseEntity.ok().body("Coucou");
//    }
}
