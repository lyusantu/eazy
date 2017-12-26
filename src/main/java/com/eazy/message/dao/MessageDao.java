package com.eazy.message.dao;

import com.eazy.commons.Page;
import com.eazy.message.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageDao {

    int addMsg(@Param("message") Message message);

    List<Message> listMyMsg(@Param("uid") int uid,@Param("page") Page page);

    void emptyStatus(@Param("uid") int uid);

    int countMyMsg(@Param("uid") int uid);

    void removeMsg(@Param("id") String id,@Param("uid") int uid);

    int countMyMsgAll(@Param("uid") int uid);

}
