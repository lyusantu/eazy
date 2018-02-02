package com.eazy.post.dao;

import com.eazy.commons.Page;
import com.eazy.post.entity.Keyword;
import com.eazy.post.entity.Post;
import com.eazy.post.entity.PostRewardRecord;
import com.eazy.post.entity.PostUpdateRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostDao {

    int addPost(@Param("post") Post post);

    /**
     * 所有帖
     *
     * @return
     */
    List<Post> list(@Param("page") Page page, @Param("tab") String tab, @Param("tab2") String tab2, @Param("type") String type);

    /**
     * 统计数量
     *
     * @return
     */
    int count(@Param("tab") String tab, @Param("tab2") String tab2, @Param("type") String type);

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
     *
     * @param uid
     * @return
     */
    int countMyPost(@Param("uid") int uid);

    /**
     * 获取帖子详情
     *
     * @param id
     * @return
     */
    Post getPost(@Param("id") int id);

    /**
     * 删除帖子
     *
     * @param id
     */
    void delete(@Param("id") int id);

    /**
     * 修改
     *
     * @param post
     */
    void update(@Param("post") Post post);

    /**
     * 置顶加精
     *
     * @param id
     * @param rank
     * @param field
     */
    void set(@Param("id") String id, @Param("rank") String rank, @Param("field") String field);

    /**
     * 本周热帖
     *
     * @return
     */
    List<Post> weeklyTop();

    /**
     *  添加keyword
     * @param keyWord
     * @return
     */
    int addKeyword(@Param("keyword") Keyword keyWord);

    /**
     * 主题的关键字
     * @param pid
     * @return
     */
    List<Keyword> getKeyword(@Param("pid") Integer pid);

    /**
     * 删除关键字
     * @param pid
     */
    void delKeyword(@Param("pid") Integer pid);

    List<Post> listTags(@Param("page") Page page, @Param("type") String type,@Param("tag") String tag);

    int countTags(@Param("type") String type, @Param("tag") String tag);

    List<Post> listSearch(@Param("page") Page page, @Param("type") String type,@Param("search") String search);

    int countSearch(@Param("type") String type, @Param("search") String search);

    int addPostUpdateRecord(@Param("pur") PostUpdateRecord postUpdateRecord);

    List<PostUpdateRecord> listPostUpdateRecord(@Param("pid") int pid);

    int addPostRewardRecord(@Param("pr") PostRewardRecord postRewardRecord);

    int rewardCount(@Param("pid") Integer pid);

    int isReward(@Param("uid") Integer uid,@Param("pid") Integer pid);

    int countAllPost();

    int countFollow(@Param("uid") int uid);

    List<Post> listFollow(@Param("uid") int uid, @Param("page") Page page);
}
