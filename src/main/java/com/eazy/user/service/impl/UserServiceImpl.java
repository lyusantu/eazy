package com.eazy.user.service.impl;

import com.eazy.user.dao.UserDao;
import com.eazy.user.entity.User;
import com.eazy.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Override
    public User login(User user) {
        return user;
    }

    @Override
    public int reg(User user) {
        return userDao.reg(user);
    }

    @Override
    public boolean verifyAccountExists(User user) {
        return userDao.verifyAccountExists(user) > 0;
    }
}
