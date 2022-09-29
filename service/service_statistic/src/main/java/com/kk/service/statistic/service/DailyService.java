package com.kk.service.statistic.service;

import com.kk.service.statistic.pojo.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author kk
 * @since 2022-09-30
 */
public interface DailyService extends IService<Daily> {
    public abstract Boolean createStatisticsByDay(String day);
    public abstract HashMap<String, Object> showChart(String begin, String end);
}
