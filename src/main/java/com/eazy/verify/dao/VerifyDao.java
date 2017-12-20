package com.eazy.verify.dao;

import com.eazy.verify.entity.Verify;
import org.apache.ibatis.annotations.Param;

public interface VerifyDao {

    Verify randVerify();

    Verify getVerify(@Param("verify") Verify verify);

}
