package com.eazy.user.service;

import com.eazy.user.entity.User;
import com.eazy.user.entity.UserFB;

import java.util.List;

public interface UserService {

    User getUser(User user);

    User login(User user);

    int reg(User user);

    boolean verifyAccountExists(User user);

    boolean verifyNickNameExists(User user);

    void update(User user);

    User activeAccount(String code);

    User verifyEmail(String email);

    void updateActiveCode(User user);

    User getUserByName(String name);

    List<User> recentlyJoined();

    int countUser();

    int insertUserFB(UserFB ufb);

    void delUserFB(UserFB ufb);

    int countUserFB(UserFB ufb);

}
