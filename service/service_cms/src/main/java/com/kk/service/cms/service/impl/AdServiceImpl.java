package com.kk.service.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kk.service.cms.pojo.Ad;
import com.kk.service.cms.mapper.AdMapper;
import com.kk.service.cms.service.AdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 广告位置 服务实现类
 * </p>
 *
 * @author kk
 * @since 2022-09-23
 */
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements AdService {

    @Override
    public IPage<Ad> selectPage(Long current, Long size) {
        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("a.type_id", "a.sort");
        return null;
    }

    @Override
    @Cacheable(value = "index", key = "'selectByAdPositionId'")
    public List<Ad> selectByAdPositionId(String adPositionId) {
        QueryWrapper<Ad> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort", "id");
        queryWrapper.eq("position_id", adPositionId);
        return baseMapper.selectList(queryWrapper);
    }
}
