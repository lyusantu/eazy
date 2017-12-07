package com.eazy.commons;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;

public class QiNiuUtil {

    Auth auth = Auth.create(QiniuAccountMgr.ACCESS_KEY, QiniuAccountMgr.SECRET_KEY); //密钥配置

    Configuration cfg = new Configuration(Zone.zone1());

    UploadManager uploadManager = new UploadManager(cfg); //创建上传对象

    /**
     * 获取凭证
     *
     * @param bucketName 空间名称
     * @return
     */
    public String getUpToken(String bucketName) {
        return auth.uploadToken(bucketName);
    }

    /**
     * 上传
     *
     * @param filePath   文件路径(也可为字节数组或者File对象)
     * @param key        上传到七牛云上的文件的名称(同一个空间下,名称[key]是唯一的)
     * @param bucketName 空间名称(这里是为了获取凭证)
     * @throws IOException
     */
    public String upload(MultipartFile file) throws IOException {
        try {
            CommonsMultipartFile cf = (CommonsMultipartFile) file;
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();
            //调用put方法上传(第二个参数是指定上传图片到七牛的访问名字,不指定默认让七牛随机生成)
            Response res = uploadManager.put(fi.getStoreLocation(), null, getUpToken("lyusantu"));
            JSONObject json = JSONObject.parseObject(res.bodyString());
            return json.getString("key");
        } catch (QiniuException e) {
            Response r = e.response;
            System.out.println(r.toString()); // 请求失败时打印的异常的信息
            try {
                System.out.println(r.bodyString()); //响应的文本信息
            } catch (QiniuException e1) {
                //ignore
            } finally {
                return null;
            }
        }
    }

}
