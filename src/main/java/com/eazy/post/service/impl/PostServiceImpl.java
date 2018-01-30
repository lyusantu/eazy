package com.eazy.post.service.impl;

import com.eazy.commons.Page;
import com.eazy.post.dao.PostDao;
import com.eazy.post.entity.Keyword;
import com.eazy.post.entity.Post;
import com.eazy.post.entity.PostRewardRecord;
import com.eazy.post.entity.PostUpdateRecord;
import com.eazy.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

@Transactional
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Override
    public int addPost(Post post) {
        return postDao.addPost(post);
    }

    @Override
    public int count(String tab, String tab2, String type) {
        return postDao.count(tab, tab2, type);
    }

    @Override
    public List<Post> list(Page page, String tab, String tab2, String type) {
        return postDao.list(page, tab, tab2, type);
    }

    @Override
    public int countMyPost(int uid) {
        return postDao.countMyPost(uid);
    }

    @Override
    public List<Post> listMyPost(int uid, Page page) {
        return postDao.listMyPost(uid, page);
    }

    @Override
    public Post getPost(int id) {
        return postDao.getPost(id);
    }

    @Override
    public void delete(int id) {
        postDao.delete(id);
    }

    @Override
    public void update(Post post) {
        postDao.update(post);
    }

    @Override
    public void set(String id, String rank, String field) {
        postDao.set(id, rank, field);
    }

    @Override
    @Cacheable(value = "myCache", key = "'weeklyTopPost'")
    public List<Post> weeklyTop() {
        return postDao.weeklyTop();
    }

    @Override
    public int addKeyword(Keyword keyWord) {
        return postDao.addKeyword(keyWord);
    }

    @Override
    public List<Keyword> getKeyword(Integer pid) {
        return postDao.getKeyword(pid);
    }

    @Override
    public void delKeyword(Integer pid) {
        postDao.delKeyword(pid);
    }

    @Override
    public List<Post> listTags(Page page, String type, String tag) {
        return postDao.listTags(page, type, tag);
    }

    @Override
    public int countTags(String type, String tag) {
        return postDao.countTags(type, tag);
    }

    @Override
    public List<Post> listSearch(Page page, String type, String search) {
        return postDao.listSearch(page, type, search);
    }

    @Override
    public int countSearch(String type, String search) {
        return postDao.countSearch(type, search);
    }

    @Override
    public int addPostUpdateRecord(PostUpdateRecord postUpdateRecord) {
        return postDao.addPostUpdateRecord(postUpdateRecord);
    }

    @Override
    public List<PostUpdateRecord> listPostUpdateRecord(int pid) {
        return postDao.listPostUpdateRecord(pid);
    }

    @Override
    public int addPostRewardRecord(PostRewardRecord postRewardRecord) {
        return postDao.addPostRewardRecord(postRewardRecord);
    }

    @Override
    public int rewardCount(Integer pid) {
        return postDao.rewardCount(pid);
    }

    @Override
    public int isReward(Integer uid, Integer pid) {
        return postDao.isReward(uid, pid);
    }
}
