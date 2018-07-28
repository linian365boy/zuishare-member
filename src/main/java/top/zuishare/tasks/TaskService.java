package top.zuishare.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author niange
 * @ClassName: TaskService
 * @desp: 定时任务
 * @date: 2018/7/28 上午9:08
 * @since JDK 1.7
 */

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    @Autowired
    private PyCaller pyCaller;
    /**
     * 每天凌晨00:01:00开始执行任务
     */
    @Scheduled(cron = "0 0/2 * * * ?")
    public void generateSiteMap() {
        long start = System.currentTimeMillis();
        logger.info("start generate sitemap...");
        //invoke python
        pyCaller.execPy();
        logger.info("end generate sitemap, cost time => {}ms", System.currentTimeMillis() - start);
    }

}
