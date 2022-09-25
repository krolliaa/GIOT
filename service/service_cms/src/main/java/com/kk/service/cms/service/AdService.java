package com.kk.service.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kk.service.cms.pojo.Ad;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 广告位置 服务类
 * </p>
 *
 * @author kk
 * @since 2022-09-23
 */
public interface AdService extends IService<Ad> {
    public abstract IPage<Ad> selectPage(Long current, Long size);
    public abstract List<Ad> selectByAdPositionId(String id);
}
