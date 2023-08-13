package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/common")
@Api("同用功能接口")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> update(MultipartFile file) {

        System.out.println(file.getOriginalFilename());
        String filename = file.getOriginalFilename();

        String name = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
        try {
            String url = aliOssUtil.upload(file.getBytes(), name);
            return Result.success(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(MessageConstant.UPLOAD_FAILED);
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
