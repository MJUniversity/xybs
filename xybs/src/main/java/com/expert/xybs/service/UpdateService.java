package com.expert.xybs.service;

import com.expert.xybs.util.OssUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UpdateService {

    public String fileUpload(MultipartFile file) {
        String url = OssUtil.checkImage(file);
        String[] imgUrls = url.split("\\?");

        return imgUrls[0];
    }


}
