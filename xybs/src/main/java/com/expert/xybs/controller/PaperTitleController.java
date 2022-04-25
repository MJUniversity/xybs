package com.expert.xybs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.service.PaperTitleService;
import com.expert.xybs.util.MyUtil;
import com.expert.xybs.util.Return;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/PaperTitle")
@RestController
@Transactional
@CrossOrigin
@Api(tags = "测试")
public class PaperTitleController {
    @Autowired
    private PaperTitleService paperTitleService;
    @PostMapping("/list")
    public Return list(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            Integer count = paperTitleService.count(json);
            JSONArray jsonObject = paperTitleService.list(json);
            re.setMaxPage(count);
            re.setData(jsonObject);
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
            paperTitleService.add(json);
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
            paperTitleService.del(json);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }

    @PostMapping("/xcxlist")
    public Return xcxlist(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            JSONArray xcxlist = paperTitleService.xcxlist(json);
            re.setData(xcxlist);
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
