package fr.boubou.poc.springboottemporal.activities;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class ItemActivitiesImpl implements ItemActivities {

    public String process(String string) {
        String taskId = UUID.randomUUID().toString();
        log.info("2 *** start process string {}", string);
        log.info("-> taskId : {}", taskId);
        return taskId;
    }
}
