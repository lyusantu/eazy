package com.eazy.verify.service;

import com.eazy.verify.entity.Verify;

import java.util.List;

public interface VerifyService {

    Verify randVerify();

    Verify getVerify(Verify verify);
}
