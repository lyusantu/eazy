package com.eazy.post.service.impl;

import com.eazy.post.dao.PostDao;
import com.eazy.post.entity.Post;
import com.eazy.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Override
    public int addPost(Post post) {
        return postDao.addPost(post);
    }
}
