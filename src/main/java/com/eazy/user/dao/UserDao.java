package com.eazy.user.dao;

import com.eazy.user.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

    int verifyAccountExists(@Param("user") User user);

    int reg(@Param("user") User user);

}
