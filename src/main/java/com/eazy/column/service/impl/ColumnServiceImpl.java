package com.eazy.column.service.impl;

import com.eazy.column.dao.ColumnDao;
import com.eazy.column.entity.Column;
import com.eazy.column.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    private ColumnDao columnDao;

    @Override
    @Cacheable(value = "myCache", key = "'listColumn'+#column.pid")
    public List<Column> listColumn(Column column) {
        return columnDao.listColumn(column);
    }

    @Override
    public Integer getPidById(Integer id) {
        return columnDao.getPidById(id);
    }

    @Override
    public List<Column> listColumnSecondary() {
        return columnDao.listColumnSecondary();
    }
}
