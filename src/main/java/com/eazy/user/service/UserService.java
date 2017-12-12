package com.eazy.user.service;

import com.eazy.user.entity.User;

public interface UserService {

    User getUser(User user);

    User login(User user);

    int reg(User user);

    boolean verifyAccountExists(User user);

    void update(User user);

    void deductBalance(User user);
}
