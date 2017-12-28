package com.eazy.sign.service.impl;

import com.eazy.sign.dao.SignDao;
import com.eazy.sign.entity.Sign;
import com.eazy.sign.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class SignServiceImpl implements SignService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SignDao signDao;

    @Override
    public Sign getSignInReocrd(int uid) {
        return signDao.getSignInReocrd(uid);
    }

    @Override
    public int signIn(Sign sign) {
        return signDao.signIn(sign);
    }

    @Override
    public void updateSignIn(Sign sign) {
        signDao.updateSignIn(sign);
    }

    @Override
    public List listSignInNew() {
        return signDao.listSignInNew();
    }

    @Override
    public List listSignInFast() {
        return signDao.listSignInFast();
    }

    @Override
    public List listSignInAll() {
        return signDao.listSignInAll();
    }

}
