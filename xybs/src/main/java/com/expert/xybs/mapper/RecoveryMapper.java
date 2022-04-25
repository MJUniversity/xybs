package com.expert.xybs.mapper;

import com.expert.xybs.pojo.Recovery;
import com.expert.xybs.pojo.RecoveryExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecoveryMapper {
    long countByExample(RecoveryExample example);

    int deleteByExample(RecoveryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Recovery record);

    int insertSelective(Recovery record);

    List<Recovery> selectByExample(RecoveryExample example);

    Recovery selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Recovery record, @Param("example") RecoveryExample example);

    int updateByExample(@Param("record") Recovery record, @Param("example") RecoveryExample example);

    int updateByPrimaryKeySelective(Recovery record);

    int updateByPrimaryKey(Recovery record);
}