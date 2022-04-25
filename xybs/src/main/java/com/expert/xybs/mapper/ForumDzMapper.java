package com.expert.xybs.mapper;

import com.expert.xybs.pojo.ForumDz;
import com.expert.xybs.pojo.ForumDzExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ForumDzMapper {
    long countByExample(ForumDzExample example);

    int deleteByExample(ForumDzExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ForumDz record);

    int insertSelective(ForumDz record);

    List<ForumDz> selectByExample(ForumDzExample example);

    ForumDz selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ForumDz record, @Param("example") ForumDzExample example);

    int updateByExample(@Param("record") ForumDz record, @Param("example") ForumDzExample example);

    int updateByPrimaryKeySelective(ForumDz record);

    int updateByPrimaryKey(ForumDz record);
}