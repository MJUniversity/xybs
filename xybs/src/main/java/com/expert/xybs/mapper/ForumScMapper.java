package com.expert.xybs.mapper;

import com.expert.xybs.pojo.ForumSc;
import com.expert.xybs.pojo.ForumScExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ForumScMapper {
    long countByExample(ForumScExample example);

    int deleteByExample(ForumScExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ForumSc record);

    int insertSelective(ForumSc record);

    List<ForumSc> selectByExample(ForumScExample example);

    ForumSc selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ForumSc record, @Param("example") ForumScExample example);

    int updateByExample(@Param("record") ForumSc record, @Param("example") ForumScExample example);

    int updateByPrimaryKeySelective(ForumSc record);

    int updateByPrimaryKey(ForumSc record);
}