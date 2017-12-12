package com.eazy.post.service;

import com.eazy.post.entity.Post;

import java.util.List;

public interface PostService {

    int addPost(Post post);

    List<Post> list();

}
