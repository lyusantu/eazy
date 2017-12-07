package com.eazy.sign.service;

import com.eazy.sign.entity.Sign;

public interface SignService {

    int getSignInReocrd(Integer uid);

    int signIn(Sign sign);
}
