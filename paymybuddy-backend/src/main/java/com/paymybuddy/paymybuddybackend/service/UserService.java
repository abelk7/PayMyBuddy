package com.paymybuddy.paymybuddybackend.service;

import com.paymybuddy.paymybuddybackend.model.User;

public interface UserService {
    User getUser(String userName);
    User saveUser(User user);


}
