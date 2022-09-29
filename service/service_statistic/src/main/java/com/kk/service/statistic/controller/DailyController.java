package com.kk.service.statistic.controller;

import com.kk.common.result.ResultData;
import com.kk.service.statistic.service.DailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author kk
 * @since 2022-09-30
 */
@Api(value = "统计数据接口")
@RestController
@RequestMapping(value = "/admin/statistic/daily")
@Slf4j
public class DailyController {
    @Autowired
    private DailyService dailyService;

    @ApiOperation("生成统计记录")
    @PostMapping(value = "/create/{day}")
    public ResultData createStatisticsByDay(@ApiParam("统计日期") @PathVariable(value = "day") String day) {
        Boolean statisticsByDay = dailyService.createStatisticsByDay(day);
        if (statisticsByDay) return ResultData.ok().message("数据统计生成成功");
        else return ResultData.error().message("数据统计生错错误");
    }

    @ApiOperation("生成一段时间内的统计记录")
    @GetMapping(value = "/show-chart/{begin}/{end}")
    public ResultData showChartStatistic(@ApiParam("统计开始日期") @PathVariable(value = "begin") String begin, @ApiParam("统计结束日期") @PathVariable(value = "end") String end) {
        HashMap<String, Object> chartData = dailyService.showChart(begin, end);
        if (chartData != null) return ResultData.ok().message("范围数据统计生成成功").data(chartData);
        else return ResultData.error().message("范围数据统计生错错误");
    }
}
