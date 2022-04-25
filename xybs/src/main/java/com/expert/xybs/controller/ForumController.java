package com.expert.xybs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.service.ForumService;
import com.expert.xybs.util.MyUtil;
import com.expert.xybs.util.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/Forum")
@RestController
@Transactional
@CrossOrigin
public class ForumController {
    @Autowired
    private ForumService forumService;
    @PostMapping("/add")
    public Return add(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            forumService.add(json);
            re.setCode(200);
            re.setMsg("提交成功！");
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
            forumService.del(json);
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
            Integer count = forumService.count(json);
            JSONArray list = forumService.list(json);
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

    @PostMapping("/bydata")
    public Return bydata(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            JSONObject bydata = forumService.bydata(json);
            re.setData(bydata);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/plAdd")
    public Return plAdd(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            forumService.plAdd(json);
            re.setCode(200);
            re.setMsg("提交成功！评论等待审核！");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/pllist")
    public Return pllist(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            JSONArray pllist = forumService.pllist(json);
            re.setData(pllist);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/dz")
    public Return dz(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            forumService.dz(json);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/sc")
    public Return sc(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            forumService.sc(json);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }


    @PostMapping("/userlist")
    public Return userlist(@RequestBody JSONObject json) {
        Return re = new Return();
        if (MyUtil.isEmpty(json)) {
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            JSONArray list = forumService.userlist(json);
            re.setData(list);
            re.setCode(200);
            re.setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/sclist")
    public Return sclist(@RequestBody JSONObject json) {
        Return re = new Return();
        if (MyUtil.isEmpty(json)) {
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            JSONArray list = forumService.sclist(json);
            re.setData(list);
            re.setCode(200);
            re.setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/scdel")
    public Return scdel(@RequestBody JSONObject json) {
        Return re = new Return();
        if (MyUtil.isEmpty(json)) {
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            forumService.scdel(json);
            re.setCode(200);
            re.setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }

    @PostMapping("/update")
    public Return update(@RequestBody JSONObject json) {
        Return re = new Return();
        if (MyUtil.isEmpty(json)) {
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            forumService.update(json);
            re.setCode(200);
            re.setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/userdata")
    public Return userdata(@RequestBody JSONObject json) {
        Return re = new Return();
        if (MyUtil.isEmpty(json)) {
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            JSONObject userdata = forumService.userdata(json);
            re.setData(userdata);
            re.setCode(200);
            re.setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }


}
