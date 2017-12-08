package com.eazy.sign.service;

import com.eazy.sign.entity.Sign;

import java.util.List;

public interface SignService {

    int getSignInReocrd(int uid);

    int signIn(Sign sign);

    boolean status(int uid);

    List listSignInNew();

    List listSignInFast();

    List listSignInAll();
}
