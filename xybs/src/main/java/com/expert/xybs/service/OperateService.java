package com.expert.xybs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.mapper.BaseDaoMapper;
import com.expert.xybs.mapper.OperateMapper;
import com.expert.xybs.pojo.Operate;
import com.expert.xybs.pojo.OperateExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class OperateService {
    @Autowired
    private BaseDaoMapper baseDaoMapper;
    @Autowired
    private OperateMapper operateMapper;

    public Integer add(JSONObject json) {
        String url = json.getString("url");
        Operate operate=new Operate();
        operate.setUrl(url);
        operate.setLx(0);
        operate.setCtime(new Date());
        operateMapper.insertSelective(operate);
        return 0;
    }
    public Integer del(JSONObject json) {
        Integer id = json.getInteger("id");
        operateMapper.deleteByPrimaryKey(id);
        return 0;
    }

    public List<Operate> List(JSONObject json) {
        OperateExample operateExample=new OperateExample();
        operateExample.createCriteria().andLxEqualTo(0);
        List<Operate> operates = operateMapper.selectByExample(operateExample);
        return operates;
    }

    public JSONObject zxData(JSONObject json) {

        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql("SELECT * from  operate  where lx=1");
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        JSONObject jsonObject = listArray.getJSONObject(0);
        return jsonObject;
    }

    public Integer zxupdate(JSONObject json) {
        String contents = json.getString("contents");
        String title = json.getString("title");
        baseDaoMapper.updateSql("update operate set title='"+title+"',contents='"+contents+"' where lx=1");
        return 0;
    }

    public JSONObject xxData(JSONObject json) {
        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql("SELECT * from  operate  where lx=2");
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        JSONObject jsonObject = listArray.getJSONObject(0);
        return jsonObject;
    }

    public Integer xxupdate(JSONObject json) {
        String contents = json.getString("contents");
        String url = json.getString("url");
        baseDaoMapper.updateSql("update operate set url='"+url+"',contents='"+contents+"' where lx=2");
        return 0;
    }




}
