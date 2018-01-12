package com.eazy.index.service;

import com.eazy.index.entity.FriendsSite;
import com.eazy.index.entity.Sponsor;
import com.eazy.post.entity.Keyword;

import java.util.List;

public interface IndexService {

    List<FriendsSite> listFriendsSite();

    List<Sponsor> listSponsor(int type);

    List<Keyword> listKeyword();

}
