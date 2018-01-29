package com.eazy.accountRecord.service;

import com.eazy.accountRecord.entity.AccountRecord;

import java.util.List;

public interface AccountRecordService {

    int addAccountReocrd(AccountRecord accountRecord);

    List<AccountRecord> listAccountRecord(int uid);

}
