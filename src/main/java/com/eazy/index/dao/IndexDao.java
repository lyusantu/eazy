package com.eazy.index.dao;

import com.eazy.index.entity.FriendsSite;
import com.eazy.index.entity.Sponsor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndexDao {

    List<FriendsSite> listFriendsSite();

    List<Sponsor> listSponsor(@Param("type") int type);

}
