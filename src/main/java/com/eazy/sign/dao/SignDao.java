package com.eazy.sign.dao;

import com.eazy.sign.entity.Sign;
import org.apache.ibatis.annotations.Param;

public interface SignDao {

    /**
     * 获取用户已签到记录
     *
     * @param uid  用户ID
     * @return
     */
    int getSignInReocrd( Integer uid);

    /**
     * 用户签到
     *
     * @param sign
     * @return
     */
    int signIn(@Param("sign") Sign sign);

    /**
     * 删除用户签到记录
     */
    void deleteSignInRecord();
}
