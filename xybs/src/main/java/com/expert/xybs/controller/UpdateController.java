package com.expert.xybs.controller;

import com.expert.xybs.service.UpdateService;
import com.expert.xybs.util.Return;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//文件上传
@RequestMapping("/update")
@RestController
@Transactional
@CrossOrigin
@Api(tags = "上传")
public class UpdateController {
    @Autowired
    private UpdateService updateService;
    @PostMapping("/imges")
    public Return imges(@RequestBody MultipartFile file){
        Return re = new Return();

        try {
//            System.out.println("进来了");
            String list = updateService.fileUpload(file);
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


}
