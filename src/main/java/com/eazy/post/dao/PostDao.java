package com.eazy.post.dao;

import com.eazy.post.entity.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostDao {

    int addPost(@Param("post") Post post);

    /**
     * 所有帖
     * @return
     */
    List<Post> list();
}
