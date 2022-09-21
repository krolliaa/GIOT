package com.kk.service.oss.service;

import java.io.InputStream;
import java.util.List;

public interface FileService {
    /**
     * 上传文件到阿里云 OSS
     *
     * @param inputStream 文件流
     * @param module 文件夹
     * @param originalFileName 文件名
     * @return 返回上传到 OSS 的 URL 地址
     */
    public abstract String upload(InputStream inputStream, String module, String originalFileName);

    /**
     * 根据文件名删除文件
     * @param avatarUrl 文件名
     * @return 是否删除成功的布尔值
     */
    public abstract Boolean deleteFile(String avatarUrl);

    /**
     * 根据文件名批量删除文件
     * @param avatarUrlList 批量删除集合
     * @return 是否删除成功的布尔值
     */
    public abstract Boolean deleteFile(List<String> avatarUrlList);
}
