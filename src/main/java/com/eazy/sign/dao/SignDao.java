package com.eazy.sign.dao;

import com.eazy.sign.entity.Sign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SignDao {

    /**
     * 获取用户已签到记录
     *
     * @param uid  用户ID
     * @return
     */
    Sign getSignInReocrd(int uid);

    /**
     * 用户签到
     *
     * @param sign
     * @return
     */
    int signIn(@Param("sign") Sign sign);

    /**
     * 更改用户签到日期
     * @param sign
     */
    void updateSignIn(@Param("sign") Sign sign);

    /**
     * 删除用户签到记录
     */
    void deleteSignInRecord();

    /**
     * 最新签到TOP20
     * @return
     */
    List listSignInNew();

    /**
     * 最快签到TOP20
     * @return
     */
    List listSignInFast();

    /**
     * 总签到榜TOP20
     * @return
     */
    List listSignInAll();
}
