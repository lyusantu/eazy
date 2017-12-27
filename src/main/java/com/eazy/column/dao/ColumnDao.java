package com.eazy.column.dao;

import com.eazy.column.entity.Column;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ColumnDao {

    List<Column> listColumn(@Param("column") Column column);

}
