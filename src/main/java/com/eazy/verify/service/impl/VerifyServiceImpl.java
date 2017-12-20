package com.eazy.verify.service.impl;

import com.eazy.verify.dao.VerifyDao;
import com.eazy.verify.entity.Verify;
import com.eazy.verify.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerifyServiceImpl implements VerifyService {

    @Autowired
    private VerifyDao verifyDao;

    @Override
    public Verify randVerify() {
        return verifyDao.randVerify();
    }

    @Override
    public Verify getVerify(Verify verify) {
        return verifyDao.getVerify(verify);
    }
}
