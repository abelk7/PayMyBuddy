package com.paymybuddy.paymybuddybackend.service;

import com.paymybuddy.paymybuddybackend.model.User;

public interface IUserService {
    User getUser(String email);
    User saveUser(User user);


}
