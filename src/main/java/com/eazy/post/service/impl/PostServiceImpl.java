package com.eazy.post.service.impl;

import com.eazy.commons.Page;
import com.eazy.post.dao.PostDao;
import com.eazy.post.entity.Keyword;
import com.eazy.post.entity.Post;
import com.eazy.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Override
    public int addPost(Post post) {
        return postDao.addPost(post);
    }

    @Override
    public List<Post> list(Page page, String tab, String tab2, String type) {
        return postDao.list(page, tab, tab2, type);
    }

    @Override
    public int count(String tab, String tab2, String type) {
        return postDao.count(tab, tab2, type);
    }

    @Override
    public List<Post> listMyPost(int uid, Page page) {
        return postDao.listMyPost(uid, page);
    }

    @Override
    public int countMyPost(int uid) {
        return postDao.countMyPost(uid);
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
}
