package fr.boubou.poc.springboottemporal.activities;

import io.temporal.failure.ApplicationFailure;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatusActivitiesImpl implements StatusActivities {

    public String pollStringProcessStatus(String taskId) {
        log.info("3 *** poll status for taskId : {}", taskId);
        double r = Math.random(); // r ∈ [0, 1)
        if (r < 0.6) {
            log.info(" -> no status for taskId : {}", taskId);
            throw ApplicationFailure.newFailure("not-ready", "PENDING");
        } else if (r < 0.9) {
            log.info(" -> status : SUCCESS for taskId : {}", taskId);
            return "SUCCESS";
        } else {
            log.info(" -> status : FAILED for taskId : {}", taskId);
            return "FAILED";
        }
    }
}
