package com.eazy.message.service.impl;

import com.eazy.commons.Page;
import com.eazy.message.dao.MessageDao;
import com.eazy.message.entity.Message;
import com.eazy.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public int addMsg(Message message) {
        return messageDao.addMsg(message);
    }

    @Override
    public List<Message> listMyMsg(int uid, Page page) {
        return messageDao.listMyMsg(uid, page);
    }

    @Override
    public void emptyStatus(int uid) {
        messageDao.emptyStatus(uid);
    }

    @Override
    public int countMyMsg(int uid) {
        return messageDao.countMyMsg(uid);
    }

    @Override
    public void removeMsg(String id, int uid) {
        messageDao.removeMsg(id, uid);
    }

    @Override
    public int countMyMsgAll(int uid) {
        return messageDao.countMyMsgAll(uid);
    }

    @Override
    public Message getMsg(int id, int uid) {
        return messageDao.getMsg(id, uid);
    }


}
