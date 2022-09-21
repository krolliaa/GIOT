package com.kk.service.oss.controller;

import com.kk.common.result.ResultData;
import com.kk.common.result.ResultEnum;
import com.kk.common.util.ExceptionUtils;
import com.kk.service.base.exception.GiotException;
import com.kk.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Api(value = "文件上传接口", description = "文件上传接口")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/admin/oss/file")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 允许多文件上传
     *
     * @return
     */
    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/upload")
    public ResultData upload(@ApiParam(value = "文件", required = true) MultipartFile file, @ApiParam(value = "文件夹", required = true) @RequestParam(value = "module") String module) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFileName = file.getOriginalFilename();
            String uploadUrl = fileService.upload(inputStream, module, originalFileName);
            return ResultData.ok().message("文件上传成功！").data("url", uploadUrl);
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new GiotException(ResultEnum.FILE_UPLOAD_ERROR);
        }
    }

    @ApiOperation(value = "删除文件")
    @DeleteMapping(value = "/deleteOneFile")
    public ResultData deleteFile(@ApiParam(value = "头像URL", required = true) @RequestBody String avatarUrl) {
        Boolean isDeleteFile = fileService.deleteFile(avatarUrl);
        if (isDeleteFile) {
            return ResultData.ok().message("文件删除成功");
        } else {
            return ResultData.error().message("文件删除错误");
        }
    }

    @ApiOperation(value = "批量删除文件")
    @DeleteMapping(value = "/deleteManyFiles")
    public ResultData deleteFile(@ApiParam(value = "头像URL集合", required = true) @RequestBody List<String> avatarUrlList) {
        Boolean isDeleteFile = fileService.deleteFile(avatarUrlList);
        if (isDeleteFile) {
            return ResultData.ok().message("文件删除成功");
        } else {
            return ResultData.error().message("文件删除错误");
        }
    }

    @ApiOperation(value = "测试远程调用")
    @GetMapping(value = "/test")
    public ResultData test() throws InterruptedException {
        log.info("OpenFeign 远程调用");
        return ResultData.ok().message("OpenFeign 服务调用成功！");
    }
}
