package com.eazy.post.service;

import com.eazy.commons.Page;
import com.eazy.post.entity.Post;

import java.util.List;

public interface PostService {

    int addPost(Post post);

    List<Post> list(Page page, String column, String type);

    int count(String column, String type);

    List<Post> listMyPost(int uid, Page page);

    int countMyPost(int uid);

    Post getPost(int id);

    void delete(int id);

    void update(Post post);

    void set(String id, String rank, String field);

    List<Post> weeklyTop();
}
