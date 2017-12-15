package com.eazy.post.dao;

import com.eazy.commons.Page;
import com.eazy.post.entity.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostDao {

    int addPost(@Param("post") Post post);

    /**
     * 所有帖
     *
     * @return
     */
    List<Post> list(@Param("page") Page page, @Param("column") String column, @Param("type") String type, @Param("order") String order);

    /**
     * 统计数量
     *
     * @param column
     * @param type
     * @return
     */
    int count(@Param("column") String column, @Param("type") String type);

    /**
     * 我发的帖
     *
     * @param uid
     * @param page
     * @return
     */
    List<Post> listMyPost(@Param("uid") int uid, @Param("page") Page page);

    /**
     * 我发的帖的数量统计
     * @param uid
     * @return
     */
    int countMyPost(@Param("uid") int uid);

    /**
     * 获取帖子详情
     * @param id
     * @return
     */
    Post getPost(@Param("id") int id);
}
