package com.expert.xybs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.expert.xybs.service.PaperTitleSubjectService;
import com.expert.xybs.util.MyUtil;
import com.expert.xybs.util.Return;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/PaperTitleSubject")
@RestController
@Transactional
@CrossOrigin
@Api(tags = "题目")
public class PaperTitleSubjectController {
    @Autowired
    private PaperTitleSubjectService paperTitleSubjectService;
    @PostMapping("/list")
    public Return list(@RequestBody JSONObject json){
        Return re = new Return();
        if(MyUtil.isEmpty(json)){
            re.setMsg("传参数有误");
            re.setCode(400);
            return re;
        }
        try {
            JSONArray jsonObject = paperTitleSubjectService.list(json);
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
            paperTitleSubjectService.add(json);
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
            paperTitleSubjectService.del(json);
            re.setCode(200);
            re.setMsg("成功");
        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }
    @PostMapping("/file")
    public Return file(@RequestBody MultipartFile file,Long id) throws Exception{
        Return re = new Return();
        System.out.println(id);
        try {
            String name=file.getOriginalFilename();
            if(!name.substring(name.length()-4).equals("xlsx")){
                re.setCode(202);
                re.setMsg("文件解析错误");
            }else {

//                System.out.println(file.getInputStream());
                paperTitleSubjectService.excelToShopIdList(file.getInputStream(),id);
                re.setCode(200);
                re.setMsg("成功");
            }

        }catch  (Exception e) {
            e.printStackTrace();
            re.setCode(400);
            re.setMsg("操作失败");
        }
        return re;
    }




}
