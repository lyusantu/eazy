package com.eazy.post.dao;

import com.eazy.post.entity.Post;
import org.apache.ibatis.annotations.Param;

public interface PostDao {

    int addPost(@Param("post") Post post);

}
