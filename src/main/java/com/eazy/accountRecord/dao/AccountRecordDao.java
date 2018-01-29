package com.eazy.accountRecord.dao;

import com.eazy.accountRecord.entity.AccountRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AccountRecordDao {

    int addAccountReocrd(@Param("accountRecord") AccountRecord accountRecord);

    List<AccountRecord> listAccountRecord(@Param("uid") int uid);

}
