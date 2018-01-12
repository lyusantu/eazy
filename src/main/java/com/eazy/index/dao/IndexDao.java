package com.eazy.index.dao;

import com.eazy.index.entity.FriendsSite;
import com.eazy.index.entity.Sponsor;
import com.eazy.post.entity.Keyword;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndexDao {

    List<FriendsSite> listFriendsSite();

    List<Sponsor> listSponsor(@Param("type") int type);

    List<Keyword> listKeyword();

}
