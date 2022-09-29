package com.kk.service.statistic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.RandomUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.feign_api.client.UCenterClient;
import com.kk.service.statistic.mapper.DailyMapper;
import com.kk.service.statistic.pojo.Daily;
import com.kk.service.statistic.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author kk
 * @since 2022-09-30
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UCenterClient uCenterClient;

    @Override
    public Boolean createStatisticsByDay(String day) {
        // 如果存在当前日志的记录则直接删除掉掉
        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        baseMapper.delete(queryWrapper);
        //获取注册人数、登录人数、课程人数、播放人数
        Integer registerNum = Math.toIntExact(uCenterClient.countRegisterNum(day));
        Integer videoViewNum = RandomUtils.nextInt(100, 200);
        Integer courseNum = RandomUtils.nextInt(100, 200);
        Integer loginNum = RandomUtils.nextInt(100, 200);
        Daily daily = new Daily();
        daily.setDateCalculated(day);
        daily.setCourseNum(courseNum);
        daily.setRegisterNum(registerNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setLoginNum(loginNum);
        int insert = baseMapper.insert(daily);
        return insert > 0;
    }

    @Override
    public HashMap<String, Object> showChart(String begin, String end) {
        HashMap<String, Object> chartData = new HashMap<>();
        Map<String, Object> registerNum = this.getChartDataByType(begin, end, "register_num");
        Map<String, Object> loginNum = this.getChartDataByType(begin, end, "login_num");
        Map<String, Object> videoViewNum = this.getChartDataByType(begin, end, "video_view_num");
        Map<String, Object> courseNum = this.getChartDataByType(begin, end, "course_num");
        chartData.put("registerNum", registerNum);
        chartData.put("loginNum", loginNum);
        chartData.put("videoViewNum", videoViewNum);
        chartData.put("courseNum", courseNum);
        return chartData;
    }

    private Map<String, Object> getChartDataByType(String begin, String end, String type) {

        HashMap<String, Object> map = new HashMap<>();

        ArrayList<String> xList = new ArrayList<>();//日期列表
        ArrayList<Integer> yList = new ArrayList<>();//数据列表

        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        //select register_num, date_calculated from statistic_daily
        queryWrapper.select(type, "date_calculated");
        queryWrapper.between("date_calculated", begin, end);

        List<Map<String, Object>> mapsData = baseMapper.selectMaps(queryWrapper);
        for (Map<String, Object> data : mapsData) {

            String dateCalculated = (String)data.get("date_calculated");
            xList.add(dateCalculated);

            Integer count = (Integer) data.get(type);
            yList.add(count);
        }

        map.put("xData", xList);
        map.put("yData", yList);
        return map;
    }
}
