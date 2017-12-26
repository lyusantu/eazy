package com.eazy.post.service.impl;

import com.eazy.commons.Page;
import com.eazy.post.dao.ReplyDao;
import com.eazy.post.entity.Reply;
import com.eazy.post.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyDao replyDao;

    @Override
    public int addReply(Reply reply) {
        return replyDao.addReply(reply);
    }

    @Override
    public List<Reply> listReply(Reply reply, Page page) {
        return replyDao.listReply(reply, page);
    }

    @Override
    public int countListReply(Reply reply) {
        return replyDao.countListReply(reply);
    }

    @Override
    public void update(String id) {
        replyDao.update(id);
    }

    @Override
    public Reply getReply(Reply reply) {
        return replyDao.getReply(reply);
    }

    @Override
    public void delReply(Reply reply) {
        replyDao.delReply(reply);
    }

    @Override
    public List<Reply> weeklyTop() {
        return replyDao.weeklyTop();
    }

}
