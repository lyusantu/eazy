package com.eazy.column.service;

import com.eazy.column.entity.Column;

import java.util.List;

public interface ColumnService {

    List<Column> listColumn(Column column);

    Integer getPidById(Integer id);

    List<Column> listColumnSecondary();

}
