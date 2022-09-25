package com.kk.service.cms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.common.result.ResultData;
import com.kk.service.cms.pojo.AdPosition;
import com.kk.service.cms.service.AdPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 广告位置 前端控制器
 * </p>
 *
 * @author kk
 * @since 2022-09-23
 */
@Api(description = "广告位置接口")
@CrossOrigin
@RestController
@RequestMapping("/admin/cms/ad-position")
@Slf4j
public class AdPositionController {

    @Autowired
    private AdPositionService adPositionService;

    @ApiOperation("广告位置列表")
    @GetMapping(value = "/list")
    public ResultData getList() {
        return ResultData.ok().data("items", adPositionService.list());
    }

    /**
     * 分页查询广告列表
     *
     * @param current
     * @param size
     * @return
     */
    @ApiOperation("广告位置分页列表")
    @GetMapping(value = "/pageList/{current}/{size}")
    public ResultData getPageList(@ApiParam(value = "当前页码", required = true) @PathVariable(value = "current") Long current, @ApiParam(value = "查询条数", required = true) @PathVariable(value = "size") Long size) {
        Page<AdPosition> adPositionPage = adPositionService.page(new Page<AdPosition>(current, size), null);
        List<AdPosition> adPositionList = adPositionPage.getRecords();
        long total = adPositionPage.getTotal();
        return ResultData.ok().data("total", total).data("rows", adPositionList);
    }

    @ApiOperation("根据 ID 获取广告位置")
    @GetMapping(value = "/{id}")
    public ResultData getById(@ApiParam(value = "广告位置 ID", required = true) @PathVariable(value = "id") String id) {
        AdPosition adPosition = adPositionService.getById(id);
        if(adPosition != null) return ResultData.ok().data("item", adPosition);
        else return ResultData.error().message("数据不存在");
    }

    /**
     * 新增广告位置
     *
     * @param adPosition
     * @return
     */
    @ApiOperation("新增广告位置")
    @PostMapping(value = "/save")
    public ResultData save(@ApiParam(value = "广告位置对象", required = true) @RequestBody AdPosition adPosition) {
        boolean result = adPositionService.save(adPosition);
        if (result) return ResultData.ok().message("保存成功");
        else return ResultData.ok().message("保存失败");
    }

    /**
     * 更新广告位置
     *
     * @param adPosition
     * @return
     */
    @ApiOperation("更新广告位置")
    @PutMapping(value = "/update")
    public ResultData update(@ApiParam(value = "广告位置对象", required = true) @RequestBody AdPosition adPosition) {
        boolean result = adPositionService.updateById(adPosition);
        if (result) return ResultData.ok().message("更新成功");
        else return ResultData.ok().message("更新失败");
    }

    /**
     * 根据 ID 删除广告位置
     *
     * @param id
     * @return
     */
    @ApiOperation("删除广告位置")
    @DeleteMapping(value = "/remove/{id}")
    public ResultData removeById(@ApiParam(value = "广告位置 ID", required = true) @PathVariable(value = "id") String id) {
        boolean result = adPositionService.removeById(id);
        if (result) return ResultData.ok().message("删除成功");
        else return ResultData.ok().message("删除失败");
    }
}
