package com.expert.xybs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.mapper.*;
import com.expert.xybs.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ForumService {
    @Autowired
    private BaseDaoMapper baseDaoMapper;
    @Autowired
    private ForumMapper forumMapper;
    @Autowired
    private ForumPlMapper forumPlMapper;
    @Autowired
    private ForumDzMapper forumDzMapper;
    @Autowired
    private ForumScMapper forumScMapper;

    public  Integer add(JSONObject json) {
        String title = json.getString("title");
        String content = json.getString("content");
        String picture = json.getString("picture");
        Integer labelsid = json.getInteger("labelsid");
        Integer uid = json.getInteger("uid");
        Integer recommend = json.getInteger("recommend");
        Integer choice = json.getInteger("choice");
        Forum forum=new Forum();
        forum.setTitle(title);
        forum.setContent(content);
        forum.setPicture(picture);
        forum.setLabelsid(labelsid);
        forum.setUid(uid);
        forum.setRecommend(recommend);
        forum.setChoice(choice);
        forum.setCtime(new Date());
        forumMapper.insertSelective(forum);
        return 0;
    }

    public Integer count(JSONObject json) {
        String title = json.getString("title");
        Integer lx = json.getInteger("lx");
        Integer labelsid = json.getInteger("labelsid");
        Integer uid = json.getInteger("uid");
        String tj="";
        if (title!=null){ tj=tj+"  and  content like  '%"+title+"%'"; }
        if (lx.equals(0)){//全部

        }else if (lx.equals(1)){//最近

        }else if (lx.equals(2)){//推荐
            tj=tj+"  and  recommend=1";
        }else if (lx.equals(3)){//精选
            tj=tj+"  and  choice=1";
        }
        if (labelsid>0){
            tj=tj+"  and  labelsid="+labelsid+"";
        }

        String SQL="SELECT count(*) as mun from  forum   where aid=0 "+tj+"    ";
        Integer integer = baseDaoMapper.selectCountSql(SQL);
        return integer;
    }
    public JSONArray list(JSONObject json) {
        Integer page = json.getInteger("page");
        Integer limit = json.getInteger("limit");
        String title = json.getString("title");
        Integer lx = json.getInteger("lx");
        Integer uid = json.getInteger("uid");
        Integer labelsid = json.getInteger("labelsid");
        String tj="";
        if (title!=null){ tj=tj+"  and  a.content like  '%"+title+"%'"; }
        String tjs=" ORDER BY  a.id  desc";
        if (lx.equals(0)){//全部

        }else if (lx.equals(1)){//最近
            tjs=" ORDER BY  a.ctime  desc";
        }else if (lx.equals(2)){//推荐
            tj=tj+"  and  a.recommend=1";
        }else if (lx.equals(3)){//精选
            tj=tj+"  and  a.choice=1";
        }
        if (labelsid>0){
            tj=tj+"  and  a.labelsid="+labelsid+"";
        }

        String SQL="SELECT a.*,b.`name`,u.`name` umame,u.logo," +
                " (SELECT COUNT(d.id)as dz from forum_dz d where d.forumid=a.id and d.uid="+uid+") as dz," +
                "(SELECT COUNT(s.id)as sc from forum_sc s where s.forumid=a.id and s.uid="+uid+" ) as sc " +
                " from  forum a LEFT JOIN labels b on a.labelsid=b.id LEFT JOIN `user` u on a.uid=u.id  where a.aid=0  "+tj+"   "+tjs+"  LIMIT "+(page-1)*limit+","+limit+"";
        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql(SQL);
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        return listArray;
    }

    public  Integer del(JSONObject json) {

        Integer id = json.getInteger("id");
        forumMapper.deleteByPrimaryKey(id);
        return 0;
    }


    public  JSONObject bydata(JSONObject json) {
        Integer id = json.getInteger("id");
        baseDaoMapper.updateSql("update forum set browse=browse+1 where id="+id+"");
        String SQL="SELECT a.*,b.`name`,u.`name` umame,u.logo " +
                " from  forum a LEFT JOIN labels b on a.labelsid=b.id LEFT JOIN `user` u on a.uid=u.id  where a.id="+id+"";
        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql(SQL);
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        JSONObject jsonObject = listArray.getJSONObject(0);
        return jsonObject;
    }
    public  Integer plAdd(JSONObject json) {

        Integer id = json.getInteger("id");
        String content = json.getString("content");
        Integer uid = json.getInteger("uid");
        baseDaoMapper.updateSql("update forum set plmun=plmun+1 where id="+id+"");
        ForumPl forumPl=new ForumPl();
        forumPl.setUid(uid);
        forumPl.setForumid(id);
        forumPl.setContent(content);
        forumPl.setCtime(new Date());
        forumPlMapper.insertSelective(forumPl);
        return 0;
    }

    public  JSONArray pllist(JSONObject json) {
        Integer id = json.getInteger("id");
        String SQL="SELECT a.*,u.`name`,u.logo from forum_pl a LEFT JOIN `user` u on a.uid=u.id where a.forumid="+id+" and a.aid=0 ORDER BY a.id asc ";
        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql(SQL);
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        return listArray;
    }

    public  Integer dz(JSONObject json) {

        Integer id = json.getInteger("id");
        Integer uid = json.getInteger("uid");
        ForumDzExample forumDzExample=new ForumDzExample();
        forumDzExample.createCriteria().andUidEqualTo(uid).andForumidEqualTo(id);
        List<ForumDz> forumDzs = forumDzMapper.selectByExample(forumDzExample);
        if (forumDzs.size()==0){
            baseDaoMapper.updateSql("update forum set dzmun=dzmun+1 where id="+id+"");
            ForumDz forumDz=new ForumDz();
            forumDz.setUid(uid);
            forumDz.setForumid(id);
            forumDz.setCtime(new Date());
            forumDzMapper.insertSelective(forumDz);
        }
        return 0;
    }

    public  Integer sc(JSONObject json) {

        Integer id = json.getInteger("id");
        Integer uid = json.getInteger("uid");
        ForumScExample forumscExample=new ForumScExample();
        forumscExample.createCriteria().andUidEqualTo(uid).andForumidEqualTo(id);
        List<ForumSc> forumsc = forumScMapper.selectByExample(forumscExample);
        if (forumsc.size()==0){
            ForumSc forumSc=new ForumSc();
            forumSc.setUid(uid);
            forumSc.setForumid(id);
            forumSc.setCtime(new Date());
            forumScMapper.insertSelective(forumSc);
        }
        return 0;
    }

    public JSONArray userlist(JSONObject json) {
        Integer page = json.getInteger("page");
        Integer limit = json.getInteger("limit");
        Integer uid = json.getInteger("uid");
        String SQL="SELECT a.*,b.`name`,u.`name` umame,u.logo from  forum a LEFT JOIN labels b on a.labelsid=b.id LEFT JOIN `user` u on a.uid=u.id  where a.uid="+uid+"     ORDER BY  a.id  desc  LIMIT "+(page-1)*limit+","+limit+"";
        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql(SQL);
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        return listArray;
    }

    public JSONArray sclist(JSONObject json) {
        Integer page = json.getInteger("page");
        Integer limit = json.getInteger("limit");
        Integer uid = json.getInteger("uid");
        String SQL="SELECT a.*,b.`name`,u.`name` umame,u.logo from  forum a LEFT JOIN labels b on a.labelsid=b.id LEFT JOIN `user` u on a.uid=u.id LEFT JOIN forum_sc s on a.id=s.forumid  where s.uid="+uid+"     ORDER BY  a.id  desc  LIMIT "+(page-1)*limit+","+limit+"";
        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql(SQL);
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        return listArray;
    }

    public  Integer scdel(JSONObject json) {

        Integer id = json.getInteger("id");
        Integer uid = json.getInteger("uid");
        ForumScExample forumscExample=new ForumScExample();
        forumscExample.createCriteria().andUidEqualTo(uid).andForumidEqualTo(id);
        forumScMapper.deleteByExample(forumscExample);
        return 0;
    }


    public  Integer update(JSONObject json) {
        Integer id = json.getInteger("id");
        Integer recommend = json.getInteger("recommend");
        Integer choice = json.getInteger("choice");
        Forum forum=new Forum();
        forum.setRecommend(recommend);
        forum.setChoice(choice);
        forum.setId(id);
        forumMapper.updateByPrimaryKeySelective(forum);
        return 0;
    }


    public JSONObject userdata(JSONObject json) {
        Integer uid = json.getInteger("uid");
        JSONObject jo=new JSONObject();
        Integer integer = baseDaoMapper.selectCountSql("SELECT count(*) as mun from  forum   where uid="+uid+" ");
        Integer integer1 = baseDaoMapper.selectCountSql("SELECT SUM(plmun) as mun from  forum a LEFT JOIN forum_pl b on a.id=b.forumid   where a.uid="+uid+" ");
        Integer integer2 = baseDaoMapper.selectCountSql("SELECT SUM(dzmun)  as mun from  forum a LEFT JOIN forum_pl b on a.id=b.forumid   where a.uid="+uid+" ");
        jo.put("tiezi",integer);
        jo.put("pl",integer1);
        jo.put("dz",integer2);
        return jo;
    }

}
