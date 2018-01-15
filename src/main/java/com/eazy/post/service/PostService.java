package com.eazy.post.service;

import com.eazy.commons.Page;
import com.eazy.post.entity.Keyword;
import com.eazy.post.entity.Post;

import java.util.List;

public interface PostService {

    int addPost(Post post);

    List<Post> list(Page page, String tab, String tab2, String type);

    int count(String tab, String tab2,String type);

    List<Post> listMyPost(int uid, Page page);

    int countMyPost(int uid);

    Post getPost(int id);

    void delete(int id);

    void update(Post post);

    void set(String id, String rank, String field);

    List<Post> weeklyTop();

    int addKeyword(Keyword keyWord);

    List<Keyword> getKeyword(Integer pid);

    void delKeyword(Integer pid);

    List<Post> listTags(Page page, String type, String tag);

    int countTags(String type, String tag);

    List<Post> listSearch(Page page, String type, String tag);

    int countSearch(String type, String search);
}
