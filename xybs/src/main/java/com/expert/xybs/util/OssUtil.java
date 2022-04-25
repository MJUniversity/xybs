package com.expert.xybs.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Component
public class OssUtil {

    protected static final Logger log = LoggerFactory.getLogger(OssUtil.class);
//    private static String endpoint="http://img.ddph.top/";
//    private static String bucketName="frmimge";
//    private static String accessKeyId="LTAI5t7i8UW2Rd4d9VcBSJUt";
//    private static String accessKeySecret="VqWgQ4borJnu97t64dTmuBHUC97bBA";
//
//    //文件存储目录
//    private static String filedir = "FRMImg/";
    private static String endpoint="http://oss-cn-beijing.aliyuncs.com/";
    private static String bucketName="lvxiaom";
    private static String accessKeyId="LTAI5t6xh7p7xqJpftA4uM4p";
    private static String accessKeySecret= "AnRCh7A5D6jAFZ6q7J2nFLyiDPWh2Y";

    //文件存储目录
    private static String filedir = "imges/";

    /**
     *
     * 上传图片
     *
     * @param file
     * @return
     */
    public static String uploadImg2Oss(MultipartFile file) {
        if (file.getSize() > 10*1024 * 1024 *10000) {
            return "图片太大";//RestResultGenerator.createErrorResult(ResponseEnum.PHOTO_TOO_MAX);
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            uploadFile2OSS(inputStream, name);
//            System.out.println(name);
            return name;//RestResultGenerator.createSuccessResult(name);
        } catch (Exception e) {
            return "上传失败";//RestResultGenerator.createErrorResult(ResponseEnum.PHOTO_UPLOAD);
        }
    }

    /**
     * 上传图片获取fileUrl
     * @param instream
     * @param fileName
     * @return
     */
    private static String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
//            System.out.println(endpoint+":"+accessKeyId+":"+accessKeySecret);
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }


        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp4")) {
            return "video/mp4";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        if (FilenameExtension.equalsIgnoreCase(".zip")) {
            return "application/zip";
        }
        if (FilenameExtension.equalsIgnoreCase(".rar")) {
            return "application/rar";
        }
        return "image/jpeg";
    }

    /**
     * 获取图片路径
     * @param fileUrl
     * @return
     */
    public static String getImgUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            String url = getUrl(filedir + split[split.length - 1]);
            return url;
        }
        return null;
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public static String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }


    /**
     * 多图片上传
     * @param fileList
     * @return
     */
    public String checkList(List<MultipartFile> fileList) {
        String fileUrl = "";
        String str = "";
        String photoUrl = "";
        for(int i = 0;i< fileList.size();i++){
            fileUrl = uploadImg2Oss(fileList.get(i));
            str = getImgUrl(fileUrl);
            if(i == 0){
                photoUrl = str;
            }else {
                photoUrl += "," + str;
            }
        }
        return photoUrl.trim();
    }

    /**
     * 单个图片上传
     * @param file
     * @return
     */
    public static String checkImage(MultipartFile file){
        String fileUrl = uploadImg2Oss(file);
        String str = getImgUrl(fileUrl);
        return str.trim();
    }
    //上传视频
    public static String uploadVideo(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000)+ System.currentTimeMillis() + substring;
        try {
            InputStream stream=file.getInputStream();
            String filename=System.currentTimeMillis()+file.getOriginalFilename();
            OSSClient client=new OSSClient(endpoint,accessKeyId,accessKeySecret);
            PutObjectResult result=client.putObject(bucketName,filedir+name,stream);
            client.shutdown();
            return filedir+name;
        } catch (Exception e) {
            throw new IOException("图片上传失败");
        }
    }



}