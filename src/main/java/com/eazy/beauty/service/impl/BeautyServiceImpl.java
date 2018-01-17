package com.eazy.beauty.service.impl;

import com.eazy.beauty.dao.BeautyDao;
import com.eazy.beauty.entity.Beauty;
import com.eazy.beauty.service.BeautyService;
import com.eazy.commons.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class BeautyServiceImpl implements BeautyService {

    @Autowired
    private BeautyDao beautyDao;

    @Override
    public int countBeauty(String order, Integer uid) {
        return beautyDao.countBeauty(order, uid);
    }

    public List<Beauty> listBeauty(Page page, String order, Integer uid) {
        return beautyDao.listBeauty(page, order, uid);
    }

    @Override
    public int add(Beauty beauty) {
        return beautyDao.add(beauty);
    }

    @Override
    public void delete(int id) {
        beautyDao.delete(id);
    }

    @Override
    public void approve(int id) {
        beautyDao.approve(id);
    }

    @Override
    public void praise(int id, int praise) {
        beautyDao.praise(id, praise);
    }

    @Override
    public Beauty getBeauty(int id) {
        return beautyDao.getBeauty(id);
    }


}
