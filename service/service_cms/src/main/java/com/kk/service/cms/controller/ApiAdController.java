package com.kk.service.cms.controller;

import com.kk.common.result.ResultData;
import com.kk.service.cms.pojo.Ad;
import com.kk.service.cms.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin //解决跨域问题
@Api(description = "广告推荐")
@RestController
@RequestMapping("/api/cms/ad")
public class ApiAdController {

    @Autowired
    private AdService adService;

    @ApiOperation("根据推荐位id显示广告推荐")
    @GetMapping("list/{id}")
    public ResultData listByAdTypeId(@ApiParam(value = "推荐位id", required = true) @PathVariable String id) {
        List<Ad> ads = adService.selectByAdPositionId(id);
        return ResultData.ok().data("items", ads);
    }
}