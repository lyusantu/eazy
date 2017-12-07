package com.eazy.sign.service;

import com.eazy.sign.entity.Sign;

public interface SignService {

    int getSignInReocrd(int uid);

    int signIn(Sign sign);

    boolean status(int uid);
}
