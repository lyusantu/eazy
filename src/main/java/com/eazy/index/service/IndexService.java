package com.eazy.index.service;

import com.eazy.index.entity.FriendsSite;
import com.eazy.index.entity.Sponsor;

import java.util.List;

public interface IndexService {

    List<FriendsSite> listFriendsSite();

    List<Sponsor> listSponsor(int type);
}
