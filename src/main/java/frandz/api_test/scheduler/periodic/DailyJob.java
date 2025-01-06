package frandz.api_test.scheduler.periodic;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyJob {

    @Scheduled(cron = "0 1 0 * * ?")
    public void executeDailyTask() {
        System.out.println("Tâche quotidienne exécutée à : " + System.currentTimeMillis());
    }
}
