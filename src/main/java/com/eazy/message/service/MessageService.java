package com.eazy.message.service;

import com.eazy.commons.Page;
import com.eazy.message.entity.Message;

import java.util.List;

public interface MessageService {

    int addMsg(Message message);

    List<Message> listMyMsg(int uid, Page page);

    void emptyStatus(int uid);

    int countMyMsg(int uid);

    void removeMsg(String id,int uid);

    int countMyMsgAll(int uid);

    Message getMsg(int id, int uid);
}
