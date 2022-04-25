package com.expert.xybs.mapper;

import com.expert.xybs.pojo.PaperTitleSubject;
import com.expert.xybs.pojo.PaperTitleSubjectExample;
import com.expert.xybs.pojo.PaperTitleSubjectWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PaperTitleSubjectMapper {
    long countByExample(PaperTitleSubjectExample example);

    int deleteByExample(PaperTitleSubjectExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PaperTitleSubjectWithBLOBs record);

    int insertSelective(PaperTitleSubjectWithBLOBs record);

    List<PaperTitleSubjectWithBLOBs> selectByExampleWithBLOBs(PaperTitleSubjectExample example);

    List<PaperTitleSubject> selectByExample(PaperTitleSubjectExample example);

    PaperTitleSubjectWithBLOBs selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PaperTitleSubjectWithBLOBs record, @Param("example") PaperTitleSubjectExample example);

    int updateByExampleWithBLOBs(@Param("record") PaperTitleSubjectWithBLOBs record, @Param("example") PaperTitleSubjectExample example);

    int updateByExample(@Param("record") PaperTitleSubject record, @Param("example") PaperTitleSubjectExample example);

    int updateByPrimaryKeySelective(PaperTitleSubjectWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PaperTitleSubjectWithBLOBs record);

    int updateByPrimaryKey(PaperTitleSubject record);
}