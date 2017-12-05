package com.eazy.user.service.impl;

import com.eazy.user.entity.User;
import com.eazy.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public User login(User user) {
        return user;
    }

    @Override
    public boolean verify(User user) {
        return false;
    }

    @Override
    public int reg(User user) {
        return 0;
    }
}
