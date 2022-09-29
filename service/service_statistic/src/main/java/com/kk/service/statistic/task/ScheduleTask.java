package com.kk.service.statistic.task;

import com.kk.service.statistic.service.DailyService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class ScheduleTask {
    @Autowired
    private DailyService dailyService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void MyScheduleTask() {
        log.info("task execute 3/s");
    }

    //每天凌晨 1 时统计数据
    @Scheduled(cron = "0 0 1 * * ?")
    public void autoStatisticData() {
        String day = new DateTime().minusDays(1).toString("yyyy-MM-dd");
        dailyService.createStatisticsByDay(day);
        log.info("taskGenarateStatisticsData 统计完毕");
    }
}
