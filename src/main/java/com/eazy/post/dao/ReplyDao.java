package com.eazy.post.dao;

import com.eazy.commons.Page;
import com.eazy.post.entity.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyDao {

    int addReply(@Param("reply") Reply reply);

    List<Reply> listReply(@Param("reply") Reply reply, @Param("page") Page page);

    int countListReply(@Param("reply") Reply reply);

    void update(@Param("id") String id);

    Reply getReply(@Param("reply") Reply reply);

    void delReply(@Param("reply") Reply reply);
}
