package com.bankapp.service;

import com.bankapp.entity.User;

public interface UserService {

    User registerUser(User user);

    User login(String email, String password);
}
