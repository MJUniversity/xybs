package com.expert.xybs.mapper;

import com.expert.xybs.pojo.Forum;
import com.expert.xybs.pojo.ForumExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ForumMapper {
    long countByExample(ForumExample example);

    int deleteByExample(ForumExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Forum record);

    int insertSelective(Forum record);

    List<Forum> selectByExample(ForumExample example);

    Forum selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Forum record, @Param("example") ForumExample example);

    int updateByExample(@Param("record") Forum record, @Param("example") ForumExample example);

    int updateByPrimaryKeySelective(Forum record);

    int updateByPrimaryKey(Forum record);
}