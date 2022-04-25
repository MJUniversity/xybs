package com.expert.xybs.mapper;

import com.expert.xybs.pojo.Operate;
import com.expert.xybs.pojo.OperateExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OperateMapper {
    long countByExample(OperateExample example);

    int deleteByExample(OperateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Operate record);

    int insertSelective(Operate record);

    List<Operate> selectByExampleWithBLOBs(OperateExample example);

    List<Operate> selectByExample(OperateExample example);

    Operate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Operate record, @Param("example") OperateExample example);

    int updateByExampleWithBLOBs(@Param("record") Operate record, @Param("example") OperateExample example);

    int updateByExample(@Param("record") Operate record, @Param("example") OperateExample example);

    int updateByPrimaryKeySelective(Operate record);

    int updateByPrimaryKeyWithBLOBs(Operate record);

    int updateByPrimaryKey(Operate record);
}