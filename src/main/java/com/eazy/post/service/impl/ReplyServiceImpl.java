package com.eazy.post.service.impl;

import com.eazy.commons.Page;
import com.eazy.post.dao.ReplyDao;
import com.eazy.post.entity.Reply;
import com.eazy.post.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyDao replyDao;

    @Override
    @Caching(evict = {
                    @CacheEvict(value = "myCache", key = "'weeklyTopReply'"),
                    @CacheEvict(value = "myCache", key = "'countAllReply'")})
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
    @Caching(evict = {
            @CacheEvict(value = "myCache", key = "'weeklyTopReply'"),
            @CacheEvict(value = "myCache", key = "'countAllReply'")})
    public void delReply(Reply reply) {
        replyDao.delReply(reply);
    }

    @Override
    @Cacheable(value = "myCache", key = "'weeklyTopReply'")
    public List<Reply> weeklyTop() {
        return replyDao.weeklyTop();
    }

    @Override
    public List<Reply> listMyReply(int uid, Page page) {
        return replyDao.listMyReply(uid, page);
    }

    @Override
    @Cacheable(value = "myCache", key = "'countAllReply'")
    public int countAllReply() {
        return replyDao.countAllReply();
    }

}
