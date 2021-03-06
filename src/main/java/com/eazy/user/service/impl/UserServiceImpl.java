package com.eazy.user.service.impl;

import com.eazy.user.dao.UserDao;
import com.eazy.user.entity.User;
import com.eazy.user.entity.UserFB;
import com.eazy.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(User user) {
        return userDao.getUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.login(user);
    }

    @Override
    public int reg(User user) {
        return userDao.reg(user);
    }

    @Override
    public boolean verifyAccountExists(User user) {
        return userDao.verifyAccountExists(user) > 0;
    }

    @Override
    public boolean verifyNickNameExists(User user) {
        return userDao.verifyNickNameExists(user) > 0;
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    @CacheEvict(value = "myCache", key = "'countUser'")
    public User activeAccount(String code) {
        return userDao.activeAccount(code);
    }

    @Override
    public User verifyEmail(String email) {
        return userDao.verifyEmail(email);
    }

    @Override
    public void updateActiveCode(User user) {
        userDao.updateActiveCode(user);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public List<User> recentlyJoined() {
        return userDao.recentlyJoined();
    }

    @Override
    @Cacheable(value = "myCache", key = "'countUser'")
    public int countUser() {
        return userDao.countUser();
    }

    @Override
    public int insertUserFB(UserFB ufb) {
        return userDao.insertUserFB(ufb);
    }

    @Override
    public void delUserFB(UserFB ufb) {
        userDao.delUserFB(ufb);
    }

    @Override
    public int countUserFB(UserFB ufb) {
        return userDao.countUserFB(ufb);
    }

}
