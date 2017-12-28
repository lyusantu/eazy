package com.eazy.collection.service.impl;

import com.eazy.collection.dao.CollectionDao;
import com.eazy.collection.entity.PostCollection;
import com.eazy.collection.service.CollectionService;
import com.eazy.commons.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionDao collectionDao;

    @Override
    public int addCollection(PostCollection collection) {
        return collectionDao.addCollection(collection);
    }

    @Override
    public void removeCollection(PostCollection collection) {
        collectionDao.removeCollection(collection);
    }

    @Override
    public PostCollection verifyCollection(PostCollection collection) {
        return collectionDao.verifyCollection(collection);
    }

    @Override
    public List<PostCollection> myCollection(int uid, Page page) {
        return collectionDao.myCollection(uid, page);
    }

    public int countMyCollection(int uid) {
        return collectionDao.countMyCollection(uid);
    }
}
