package com.expert.xybs.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.mapper.BaseDaoMapper;
import com.expert.xybs.mapper.LabelsMapper;
import com.expert.xybs.pojo.Labels;
import com.expert.xybs.pojo.LabelsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelsService {
    @Autowired
    private BaseDaoMapper baseDaoMapper;
    @Autowired
    private LabelsMapper labelsMapper;
    public Integer add(JSONObject json) {
        Integer lx = json.getInteger("lx");
        String name = json.getString("name");
        String ioc = json.getString("ioc");
        Labels labels=new Labels();
        labels.setLx(lx);
        labels.setName(name);
        labels.setIoc(ioc);
        labelsMapper.insertSelective(labels);
        return 0;
    }

    public JSONArray List(JSONObject json) {
        JSONArray array=new JSONArray();
        LabelsExample labelsExample=new LabelsExample();
        labelsExample.setOrderByClause(" id asc");
        List<Labels> labels = labelsMapper.selectByExample(labelsExample);
        for (int i=0;i<labels.size();i++){
            JSONObject jo=new JSONObject();
            Integer id = labels.get(i).getId();
            String name = labels.get(i).getName();
            jo.put("lx",id);
            jo.put("name",name);
            LabelsExample labelsEx=new LabelsExample();
            labelsEx.createCriteria().andLxEqualTo(id);
            List<Labels> labels1 = labelsMapper.selectByExample(labelsEx);
            jo.put("list",labels1);
            array.add(jo);
        }
        return array;
    }

    public Integer del(JSONObject json) {
        Integer id = json.getInteger("id");
        labelsMapper.deleteByPrimaryKey(id);
        return 0;
    }
}
