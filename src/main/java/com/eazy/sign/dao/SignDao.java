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
    int getSignInReocrd(int uid);

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

    /**
     * 检查今日是否签到
     * @param uid
     * @return
     */
    int status(int uid);

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
