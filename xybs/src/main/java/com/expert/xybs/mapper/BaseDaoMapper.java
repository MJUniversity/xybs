package com.expert.xybs.mapper;

import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;


public interface BaseDaoMapper {
    @Insert("${sql}")
    int insertSql(@Param("sql") String sql);

    @Update("${sql}")
    int updateSql(@Param("sql") String sql);

    @Select("${sql}")
    List<LinkedHashMap<String, Object>> selectSql(@Param("sql") String sql);

    @Select("${sql}")
    Integer selectCountSql(@Param("sql") String sql);

    @Delete("${sql}")
    int deleteSql(@Param("sql") String sql);



}