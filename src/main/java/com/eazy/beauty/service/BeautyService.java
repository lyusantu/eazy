package com.eazy.beauty.service;

import com.eazy.beauty.entity.Beauty;
import com.eazy.commons.Page;

import java.util.List;

public interface BeautyService {

    int countBeauty(String order, Integer uid);

    List<Beauty> listBeauty(Page page, String order, Integer uid);

    int add(Beauty beauty);

    void delete(int id);

    void approve(int id);

    void praise(int id, int praise);

    Beauty getBeauty(int id);

}
