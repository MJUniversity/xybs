package com.expert.xybs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.mapper.BaseDaoMapper;
import com.expert.xybs.mapper.PaperTitleMapper;
import com.expert.xybs.pojo.PaperTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PaperTitleService {
    @Autowired
    private BaseDaoMapper baseDaoMapper;
    @Autowired
    private PaperTitleMapper paperTitleMapper;
    public Integer count(JSONObject json) {
        String title = json.getString("title");

        String tj="";
        if (title!=null){ tj=tj+"  and  title like  '%"+title+"%' "; }

        String SQL="SELECT count(*) as mun from  paper_title   where id>0 "+tj+"    ";
        Integer integer = baseDaoMapper.selectCountSql(SQL);
        return integer;
    }
    public JSONArray list(JSONObject json) {
        Integer page = json.getInteger("page");
        Integer limit = json.getInteger("limit");
        String title = json.getString("title");;
        String tj="";
        if (title!=null){ tj=tj+"  and  title like  '%"+title+"%' "; }
        String SQL="SELECT * from  paper_title   where id>0 "+tj+"   ORDER BY  id  desc   LIMIT "+(page-1)*limit+","+limit+"";
        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql(SQL);
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        return listArray;
    }
    public Integer add(JSONObject json) {

        String title = json.getString("title");
        String aname = json.getString("aname");
        String bname = json.getString("bname");
        String cname = json.getString("cname");
        String dname = json.getString("dname");
        PaperTitle paperTitle=new PaperTitle();
        paperTitle.setTitle(title);
        paperTitle.setAname(aname);
        paperTitle.setBname(bname);
        paperTitle.setCname(cname);
        paperTitle.setDname(dname);
        paperTitleMapper.insertSelective(paperTitle);
        return 0;
    }
    public Integer del(JSONObject json) {
        Long id = json.getLong("id");
        paperTitleMapper.deleteByPrimaryKey(id);
        return 0;
    }
    public JSONArray xcxlist(JSONObject json) {
        String SQL="SELECT * from  paper_title where id>0  ORDER BY  id  asc ";
        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql(SQL);
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        return listArray;
    }
}
