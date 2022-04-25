package com.expert.xybs.mapper;

import com.expert.xybs.pojo.PaperTitle;
import com.expert.xybs.pojo.PaperTitleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PaperTitleMapper {
    long countByExample(PaperTitleExample example);

    int deleteByExample(PaperTitleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PaperTitle record);

    int insertSelective(PaperTitle record);

    List<PaperTitle> selectByExample(PaperTitleExample example);

    PaperTitle selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PaperTitle record, @Param("example") PaperTitleExample example);

    int updateByExample(@Param("record") PaperTitle record, @Param("example") PaperTitleExample example);

    int updateByPrimaryKeySelective(PaperTitle record);

    int updateByPrimaryKey(PaperTitle record);
}