package com.eazy.user.dao;

import com.eazy.user.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

    User getUser(@Param("user") User user);

    User login(@Param("user") User user);

    int verifyAccountExists(@Param("user") User user);

    int reg(@Param("user") User user);

    void update(@Param("user") User user);

    void deductBalance(@Param("user") User user);
}
