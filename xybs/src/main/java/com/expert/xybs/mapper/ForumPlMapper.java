package com.expert.xybs.mapper;

import com.expert.xybs.pojo.ForumPl;
import com.expert.xybs.pojo.ForumPlExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ForumPlMapper {
    long countByExample(ForumPlExample example);

    int deleteByExample(ForumPlExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ForumPl record);

    int insertSelective(ForumPl record);

    List<ForumPl> selectByExample(ForumPlExample example);

    ForumPl selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ForumPl record, @Param("example") ForumPlExample example);

    int updateByExample(@Param("record") ForumPl record, @Param("example") ForumPlExample example);

    int updateByPrimaryKeySelective(ForumPl record);

    int updateByPrimaryKey(ForumPl record);
}