package com.eazy.collection.service;

import com.eazy.collection.entity.PostCollection;
import com.eazy.commons.Page;

import java.util.List;

public interface CollectionService {

    int addCollection(PostCollection collection);

    void removeCollection(PostCollection collection);

    PostCollection verifyCollection(PostCollection collection);

    List<PostCollection> myCollection(int uid, Page page);

    int countMyCollection(int uid);
}
