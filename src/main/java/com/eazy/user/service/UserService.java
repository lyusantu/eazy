package com.eazy.user.service;

import com.eazy.user.entity.User;

public interface UserService {

    User login(User user);

    boolean verify(User user);

    int reg(User user);
}
