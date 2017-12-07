package com.eazy.sign.service.impl;

import com.eazy.sign.dao.SignDao;
import com.eazy.sign.entity.Sign;
import com.eazy.sign.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignServiceImpl implements SignService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SignDao signDao;

    @Override
    public int getSignInReocrd(Integer uid) {
        return signDao.getSignInReocrd(uid);
    }

    @Override
    public int signIn(Sign sign) {
        return signDao.signIn(sign);
    }
}
