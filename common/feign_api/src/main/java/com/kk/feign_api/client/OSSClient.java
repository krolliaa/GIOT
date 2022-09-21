package com.kk.feign_api.client;

import com.kk.common.result.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "service-oss")
public interface OSSClient {
    @GetMapping(value = "/admin/oss/file/test")
    public abstract ResultData test();

    @DeleteMapping(value = "/admin/oss/file/deleteOneFile")
    public abstract ResultData deleteFile(String avatarUrl);

    @DeleteMapping(value = "/admin/oss/file/deleteManyFiles")
    public abstract ResultData deleteFile(List<String> avatarUrlList);
}