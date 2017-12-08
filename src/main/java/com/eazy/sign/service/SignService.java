package com.eazy.sign.service;

import com.eazy.sign.entity.Sign;

import java.util.List;

public interface SignService {

    Sign getSignInReocrd(int uid);

    int signIn(Sign sign);

    void updateSignIn(Sign sign);

    List listSignInNew();

    List listSignInFast();

    List listSignInAll();
}
