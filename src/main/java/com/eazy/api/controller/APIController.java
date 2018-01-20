package com.eazy.api.controller;

import com.eazy.commons.Constants;
import com.eazy.commons.QiNiuUtil;
import com.eazy.commons.auth.AuthPassport;
import com.eazy.commons.dto.AjaxResult;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api")
@Controller
@Api(value = "/api", tags = "理论上是一个上传接口")
public class APIController {

    @AuthPassport
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public AjaxResult upload(@Param("file") MultipartFile file) throws IOException {
        String upload = Constants.QINIU_CHAIN + new QiNiuUtil().upload(file);
        return new AjaxResult(0, null, null, upload);
    }

}
