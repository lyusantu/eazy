package com.eazy.column.service.impl;

import com.eazy.column.dao.ColumnDao;
import com.eazy.column.entity.Column;
import com.eazy.column.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    private ColumnDao postDao;

    @Override
    public List<Column> listColumn(Column column) {
        return postDao.listColumn(column);
    }
}
