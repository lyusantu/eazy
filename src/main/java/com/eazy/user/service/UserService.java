package com.eazy.user.service;

import com.eazy.user.entity.User;

public interface UserService {

    User getUser(User user);

    User login(User user);

    int reg(User user);

    boolean verifyAccountExists(User user);

    void update(User user);

    User activeAccount(String code);

    User verifyEmail(String email);

    void updateActiveCode(User user);
}
