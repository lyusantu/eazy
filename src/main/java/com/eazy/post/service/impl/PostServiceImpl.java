package com.eazy.post.service.impl;

import com.eazy.commons.Page;
import com.eazy.post.dao.PostDao;
import com.eazy.post.entity.Post;
import com.eazy.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Override
    public int addPost(Post post) {
        return postDao.addPost(post);
    }

    @Override
    public List<Post> list(Page page, String column, String type) {
        return postDao.list(page, column, type);
    }

    @Override
    public int count(String column, String type) {
        return postDao.count(column, type);
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
}
