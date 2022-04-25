package com.expert.xybs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.service.RecoveryService;
import com.expert.xybs.pojo.Recovery;
import com.expert.xybs.util.MyUtil;
import com.expert.xybs.util.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/Recovery")
@RestController
@Transactional
@CrossOrigin
public class RecoveryController {
    @Autowired
    private RecoveryService recoveryService;

    @PostMapping("/list")
    public Return list(@RequestBody JSONObject json) {
        Return re = new Return();
        if (MyUtil.isEmpty(json)) {
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            Integer count = recoveryService.count(json);
            JSONArray list = recoveryService.list(json);
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
    @PostMapping("/add")
    public Return add(@RequestBody JSONObject json) {
        Return re = new Return();
        if (MyUtil.isEmpty(json)) {
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            recoveryService.add(json);
            re.setCode(200);
            re.setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/del")
    public Return del(@RequestBody JSONObject json) {
        Return re = new Return();
        if (MyUtil.isEmpty(json)) {
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            recoveryService.del(json);
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
            recoveryService.update(json);
            re.setCode(200);
            re.setMsg("成功");
        } catch (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/ByDate")
    public Return ByDate(@RequestBody JSONObject json) {
        Return re = new Return();
        if (MyUtil.isEmpty(json)) {
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            Recovery recovery = recoveryService.ByData(json);
            re.setData(recovery);
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
