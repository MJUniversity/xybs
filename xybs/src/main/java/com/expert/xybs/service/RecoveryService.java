package com.expert.xybs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.mapper.BaseDaoMapper;
import com.expert.xybs.mapper.RecoveryMapper;
import com.expert.xybs.pojo.Recovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class RecoveryService {
    @Autowired
    private BaseDaoMapper baseDaoMapper;
    @Autowired
    private RecoveryMapper recoveryMapper;
    public Integer add(JSONObject json) {
        String title = json.getString("title");
        String ioc = json.getString("ioc");
        String video = json.getString("video");
        String content = json.getString("content");

        Recovery recovery=new Recovery();
        recovery.setTitle(title);
        recovery.setIoc(ioc);
        recovery.setVideo(video);
        recovery.setContent(content);
        recovery.setCtime(new Date());
        recoveryMapper.insertSelective(recovery);
        return 0;
    }

    public Integer count(JSONObject json) {
        String title = json.getString("title");
        String tj="";
        if (title!=null){ tj=tj+"  and  title like  '%"+title+"%'"; }
        String SQL="SELECT count(*) as num from  recovery   where 1=1 "+tj+"    ";
        Integer integer = baseDaoMapper.selectCountSql(SQL);
        return integer;
    }
    public JSONArray list(JSONObject json) {
        Integer page = json.getInteger("page");
        Integer limit = json.getInteger("limit");
        String title = json.getString("title");
        String tj="";
        if (title!=null){ tj=tj+"  and  title like  '%"+title+"%'"; }
        String SQL="SELECT * from  recovery   where 1=1 "+tj+"   ORDER BY  id  desc LIMIT "+(page-1)*limit+","+limit+"";
        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql(SQL);
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        return listArray;
    }

    public Integer update(JSONObject json) {
        Integer id = json.getInteger("id");
        String title = json.getString("title");
        String ioc = json.getString("ioc");
        String video = json.getString("video");
        String content = json.getString("content");
        Recovery recovery=new Recovery();
        recovery.setTitle(title);
        recovery.setIoc(ioc);
        recovery.setVideo(video);
        recovery.setContent(content);
        recovery.setCtime(new Date());
        recovery.setId(id);
        recoveryMapper.updateByPrimaryKeySelective(recovery);
        return 0;
    }

    public Integer del(JSONObject json) {
        Integer id = json.getInteger("id");
        recoveryMapper.deleteByPrimaryKey(id);
        return 0;
    }

    public Recovery ByData(JSONObject json) {
        Integer id = json.getInteger("id");
        Recovery recovery = recoveryMapper.selectByPrimaryKey(id);
        return recovery;
    }
}
