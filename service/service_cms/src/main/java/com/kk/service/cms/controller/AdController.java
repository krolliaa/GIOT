package com.kk.service.cms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.common.result.ResultData;
import com.kk.service.cms.pojo.Ad;
import com.kk.service.cms.pojo.AdPosition;
import com.kk.service.cms.pojo.vo.AdReturnVo;
import com.kk.service.cms.service.AdPositionService;
import com.kk.service.cms.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 广告位置 前端控制器
 * </p>
 *
 * @author kk
 * @since 2022-09-23
 */
@CrossOrigin
@Api(description = "广告推荐管理")
@RestController
@RequestMapping("/admin/cms/ad")
@Slf4j
public class AdController {
    @Autowired
    private AdService adService;

    @Autowired
    private AdPositionService adPositionService;

    @ApiOperation("新增推荐")
    @PostMapping("/save")
    public ResultData save(@ApiParam(value = "推荐对象", required = true) @RequestBody Ad ad) {
        boolean result = adService.save(ad);
        if (result) return ResultData.ok().message("保存成功");
        else return ResultData.error().message("保存失败");
    }

    @ApiOperation("更新推荐")
    @PutMapping("/update")
    public ResultData updateById(@ApiParam(value = "讲师推荐", required = true) @RequestBody Ad ad) {
        System.out.println("调用的是这个方法？？？？");
        boolean result = adService.updateById(ad);
        if (result) return ResultData.ok().message("修改成功");
        else return ResultData.error().message("数据不存在");
    }

    @ApiOperation("根据id获取推荐信息")
    @GetMapping("/{id}")
    public ResultData getById(@ApiParam(value = "推荐ID", required = true) @PathVariable String id) {
        Ad ad = adService.getById(id);
        AdPosition adPosition = adPositionService.getById(ad.getPositionId());
        AdReturnVo adReturnVo = new AdReturnVo();
        adReturnVo.setPositionId(ad.getPositionId());
        adReturnVo.setId(ad.getId());
        adReturnVo.setTitle(ad.getTitle());
        adReturnVo.setImageUrl(ad.getImageUrl());
        adReturnVo.setAdPosition(adPosition);
        adReturnVo.setColor(ad.getColor());
        adReturnVo.setLinkUrl(ad.getLinkUrl());
        adReturnVo.setSort(ad.getSort());
        if (ad != null) return ResultData.ok().data("item", adReturnVo);
        else return ResultData.error().message("数据不存在");
    }

    @ApiOperation("推荐分页列表")
    @GetMapping("/pageList/{current}/{size}")
    public ResultData getPageList(@ApiParam(value = "当前页码", required = true) @PathVariable Long current,
                                  @ApiParam(value = "每页记录数", required = true) @PathVariable Long size) {
        IPage<Ad> pageModel = adService.page(new Page<Ad>(current, size), null);
        List<Ad> records = pageModel.getRecords();
        List<AdReturnVo> ads = new ArrayList<>();
        for (Ad record : records) {
            String positionId = record.getPositionId();
            AdPosition adPosition = adPositionService.getById(positionId);
            AdReturnVo adReturnVo = new AdReturnVo();
            adReturnVo.setId(record.getId());
            adReturnVo.setPositionId(positionId);
            adReturnVo.setTitle(record.getTitle());
            adReturnVo.setImageUrl(record.getImageUrl());
            adReturnVo.setAdPosition(adPosition);
            adReturnVo.setColor(record.getColor());
            adReturnVo.setLinkUrl(record.getLinkUrl());
            adReturnVo.setSort(record.getSort());
            ads.add(adReturnVo);
        }
        long total = pageModel.getTotal();
        return ResultData.ok().data("total", total).data("rows", ads);
    }

    @ApiOperation(value = "根据ID删除推荐")
    @DeleteMapping("/{id}")
    public ResultData removeById(@ApiParam(value = "推荐ID", required = true) @PathVariable String id) {
        //删除图片 adService.removeAdImageById(id);
        boolean result = adService.removeById(id);
        if (result) return ResultData.ok().message("删除成功");
        else return ResultData.error().message("数据不存在");
    }
}
