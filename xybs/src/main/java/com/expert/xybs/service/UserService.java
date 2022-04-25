package com.expert.xybs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.mapper.BaseDaoMapper;
import com.expert.xybs.mapper.UserMapper;
import com.expert.xybs.pojo.User;
import com.expert.xybs.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.kevinsawicki.http.HttpRequest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.*;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.NamingException;

@Service
public class UserService {
    @Autowired
    private BaseDaoMapper baseDaoMapper;
    @Autowired
    private UserMapper userMapper;
    public User getOpenid(JSONObject json) {
        String code = json.getString("code");
        String logo = json.getString("logo");
        String name = json.getString("name");
        Integer sex = json.getInteger("sex");

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", "wx2efb385abd4a4903");
        data.put("secret", "84e819a7c59289894781fbf90b0219f1");
        data.put("js_code", code);
        data.put("grant_type", "authorization_code");
        String response = HttpRequest.get("https://api.weixin.qq.com/sns/jscode2session").form(data).body();
        JSONObject obj= JSON.parseObject(response);
//        System.out.println(response);
        String openid = obj.getString("openid");
        String session_key = obj.getString("session_key");
        UserExample userExample=new UserExample();
        userExample.createCriteria().andOpenidEqualTo(openid);
        List<User> users = userMapper.selectByExample(userExample);

        if (users.size()>0){
            User user = users.get(0);
            User userS=new User();
            userS.setAname(session_key);
            userS.setId(user.getId());
            userMapper.updateByPrimaryKeySelective(userS);
            UserExample userExa=new UserExample();
            userExa.createCriteria().andOpenidEqualTo(openid);
            List<User> use = userMapper.selectByExample(userExa);
            return use.get(0);
        }else {
            User user=new User();
            user.setLogo(logo);
            user.setName(name);
            user.setSex(sex);
            user.setOpenid(openid);
            user.setCtime(new Date());
            user.setLx(0);
            user.setAname(session_key);
            userMapper.insertSelective(user);
            return user;
        }


    }


    public User update(JSONObject json) {
        Integer id = json.getInteger("id");
        String logo = json.getString("logo");
        String name = json.getString("name");
        Integer sex = json.getInteger("sex");
        String username = json.getString("username");
        String password = json.getString("password");
        String telephone = json.getString("telephone");
        User user=new User();
        user.setLogo(logo);
        user.setName(name);
        user.setSex(sex);
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setTelephone(telephone);
        userMapper.updateByPrimaryKeySelective(user);
        User user1 = userMapper.selectByPrimaryKey(id);
        return user1;

    }

    public  Integer add(JSONObject json) {
        String logo = json.getString("logo");
        String name = json.getString("name");
        Integer sex = json.getInteger("sex");
        String username = json.getString("username");
        String password = json.getString("password");
        String telephone = json.getString("telephone");
        User user=new User();
        user.setLogo(logo);
        user.setName(name);
        user.setSex(sex);
        user.setLx(1);
        user.setUsername(username);
        user.setPassword(password);
        user.setCtime(new Date());
        user.setTelephone(telephone);
        userMapper.insertSelective(user);
        return 0;
    }
    public  Integer del(JSONObject json) {
        Integer id = json.getInteger("id");
        userMapper.deleteByPrimaryKey(id);
        return 0;
    }
    public Integer count(JSONObject json) {
        String name = json.getString("name");
        Integer lx = json.getInteger("lx");
        String tj="";
        if (name!=null){ tj=tj+"  and  name like  '%"+name+"%'"; }
        String SQL="SELECT count(*) as mun from  user   where lx="+lx+" "+tj+"    ";
        Integer integer = baseDaoMapper.selectCountSql(SQL);
        return integer;
    }
    public JSONArray list(JSONObject json) {
        Integer page = json.getInteger("page");
        Integer limit = json.getInteger("limit");
        String name = json.getString("name");
        Integer lx = json.getInteger("lx");
        String tj="";
        if (name!=null){ tj=tj+"  and  name like  '%"+name+"%'"; }
        String SQL="SELECT * from    user   where lx="+lx+"  "+tj+"   ORDER BY  id  desc LIMIT "+(page-1)*limit+","+limit+"";
        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql(SQL);
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        return listArray;
    }
    public JSONObject SignIn(JSONObject json) {
        String username = json.getString("username");
        String password = json.getString("password");
        JSONObject jo=new JSONObject();
//        UserExample userExample=new UserExample();
//        userExample.createCriteria().andLxEqualTo(1).andUsernameEqualTo(username).andPasswordEqualTo(password);
//        List<User> users = userMapper.selectByExample(userExample);
        String SQL="SELECT * from    user   where lx=1 and  username='"+username+"' and  password='"+password+"'";
        List<LinkedHashMap<String, Object>> linkedHashMaps = baseDaoMapper.selectSql(SQL);
        JSONArray listArray = JSON.parseArray(JSON.toJSONString(linkedHashMaps));
        if (listArray.size()>0){
            jo.put("zt","1");
            jo.put("data",listArray.getJSONObject(0));
        }else {
            jo.put("zt","0");
        }
        return jo;
    }




}
