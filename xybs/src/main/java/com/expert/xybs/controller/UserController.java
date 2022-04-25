package com.expert.xybs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.pojo.User;
import com.expert.xybs.service.UserService;
import com.expert.xybs.util.MyUtil;
import com.expert.xybs.util.Return;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/User")
@RestController
@Transactional
@CrossOrigin
@Api(tags = "用户")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/getOpenid")
    public Return getOpenid(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            User openid = userService.getOpenid(json);
            re.setData(openid);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/update")
    public Return update(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            User openid = userService.update(json);
            re.setData(openid);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/add")
    public Return add(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            userService.add(json);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/del")
    public Return del(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            userService.del(json);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/list")
    public Return list(@RequestBody JSONObject json) {
        Return re = new Return();
        if (MyUtil.isEmpty(json)) {
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            Integer count = userService.count(json);
            JSONArray list = userService.list(json);
            re.setData(list);
            re.setMaxPage(count);
            re.setCode(200);
            re.setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }


    @PostMapping("/SignIn")
    public Return SignIn(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            JSONObject jsonObject = userService.SignIn(json);
            String zt = jsonObject.getString("zt");
            if (zt=="0"){
                re.setCode(202);
                re.setMsg("账号密码不正确！");
            }else {
                String data = jsonObject.getString("data");
                re.setData(JSON.parse(data));
                re.setCode(200);
                re.setMsg("登录成功");
            }


        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }


}
