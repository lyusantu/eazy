package com.eazy.accountRecord.service.impl;


import com.eazy.accountRecord.dao.AccountRecordDao;
import com.eazy.accountRecord.entity.AccountRecord;
import com.eazy.accountRecord.service.AccountRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountRecordServiceImpl implements AccountRecordService {

    @Autowired
    private AccountRecordDao accountRecordDao;

    @Override
    public int addAccountReocrd(AccountRecord accountRecord) {
        return accountRecordDao.addAccountReocrd(accountRecord);
    }

    @Override
    public List<AccountRecord> listAccountRecord(int uid) {
        return accountRecordDao.listAccountRecord(uid);
    }
}
