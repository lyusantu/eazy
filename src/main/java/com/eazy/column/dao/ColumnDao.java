package com.eazy.column.dao;

import com.eazy.column.entity.Column;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ColumnDao {

    List<Column> listColumn(@Param("column") Column column);

    Integer getPidById(@Param("id") Integer id);

    List<Column> listColumnSecondary();

    Column getDesc(@Param("suffix") String suffix);

    void editDesc(@Param("column") Column column);

}
