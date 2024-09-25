package com.tobipeter.giftdrop.cron.jobs;

import com.tobipeter.giftdrop.cron.services.WindowMetricUpdateService;
import com.tobipeter.giftdrop.exceptions.WindowMetricUpdateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
@RequiredArgsConstructor
public class WindowMetricUpdateCron {
    private final WindowMetricUpdateService metricUpdateService;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    @Scheduled(cron = "${giftdrop.metric.update.cron.exp:* */5 * * * *}")
    @SchedulerLock(
            name = "Window_Metric_Update_Cron",
            lockAtLeastFor = "${giftdrop.metric.update.cron.min.lock:PT5M}",
            lockAtMostFor = "${giftdrop.metric.update.cron.max.lock:PT10M}"
    )
    public void start(){
        if(isRunning.get()){
            log.info(this.getClass().getSimpleName() + "job is running.");
        }
        isRunning.set(true);
        log.info("Window Metric Updates have commenced");

        try{
            metricUpdateService.update();
        }
        catch(WindowMetricUpdateException e){
            log.info(e.getMessage());
        }
        finally{
            log.info("Window Metric Updates have completed.");
            isRunning.set(false);
        }
    }
}
