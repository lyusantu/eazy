package com.eazy.collection.dao;

import com.eazy.collection.entity.PostCollection;
import com.eazy.commons.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionDao {

    int addCollection(@Param("postCollection") PostCollection postCollection);

    void removeCollection(@Param("postCollection") PostCollection collection);

    PostCollection verifyCollection(@Param("postCollection") PostCollection collection);

    List<PostCollection> myCollection(@Param("uid") int uid, @Param("page") Page page);

    int countMyCollection(@Param("uid") int uid);

}
