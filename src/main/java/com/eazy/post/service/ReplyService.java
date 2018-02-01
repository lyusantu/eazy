package com.eazy.post.service;

import com.eazy.commons.Page;
import com.eazy.post.entity.Reply;

import java.util.List;

public interface ReplyService {

    int addReply(Reply reply);

    List<Reply> listReply(Reply reply, Page page);

    int countListReply(Reply reply);

    void update(String id);

    Reply getReply(Reply reply);

    void delReply(Reply reply);

    List<Reply> weeklyTop();

    List<Reply> listMyReply(int uid, Page page);

    int countAllReply();
}
