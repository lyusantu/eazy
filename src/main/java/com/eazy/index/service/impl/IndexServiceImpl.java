package com.eazy.index.service.impl;

import com.eazy.index.dao.IndexDao;
import com.eazy.index.entity.FriendsSite;
import com.eazy.index.entity.Sponsor;
import com.eazy.index.service.IndexService;
import com.eazy.post.entity.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexDao indexDao;

    @Override
    public List<FriendsSite> listFriendsSite() {
        return indexDao.listFriendsSite();
    }

    @Override
    public List<Sponsor> listSponsor(int type) {
        return indexDao.listSponsor(type);
    }

    @Override
    public List<Keyword> listKeyword() {
        return indexDao.listKeyword();
    }
}
