package com.expert.xybs.controller;

import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.service.OperateService;
import com.expert.xybs.pojo.Operate;
import com.expert.xybs.util.MyUtil;
import com.expert.xybs.util.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/Operate")
@RestController
@Transactional
@CrossOrigin
public class OperateController {
    @Autowired
    private OperateService operateService;
    @PostMapping("/List")
    public Return List(@RequestBody JSONObject json){
        Return re = new Return();

        try {
            List<Operate> list = operateService.List(json);
            re.setData(list);
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
            operateService.add(json);
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
            operateService.del(json);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/zxData")
    public Return zxData(@RequestBody JSONObject json){
        Return re = new Return();
        try {
            JSONObject operate = operateService.zxData(json);
            re.setData(operate);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/xxData")
    public Return xxData(@RequestBody JSONObject json){
        Return re = new Return();
        try {
            JSONObject operate = operateService.xxData(json);
            re.setData(operate);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }

    @PostMapping("/zxupdate")
    public Return zxupdate(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            operateService.zxupdate(json);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/xxupdate")
    public Return xxupdate(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            operateService.xxupdate(json);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
}
