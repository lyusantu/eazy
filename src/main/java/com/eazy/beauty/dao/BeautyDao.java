package com.eazy.beauty.dao;

import com.eazy.beauty.entity.Beauty;
import com.eazy.commons.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BeautyDao {

    int countBeauty(@Param("order") String order, @Param("uid") Integer uid);

    List<Beauty> listBeauty(@Param("page") Page page, @Param("order") String order, @Param("uid") Integer uid);

    int add(@Param("beauty") Beauty beauty);

    void delete(@Param("id") int id);

    void approve(@Param("id") int id);

    void praise(@Param("id") int id,@Param("praise") int praise);

    Beauty getBeauty(@Param("id") int id);

}
