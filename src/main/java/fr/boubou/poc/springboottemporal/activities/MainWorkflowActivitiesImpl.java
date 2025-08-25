package fr.boubou.poc.springboottemporal.activities;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MainWorkflowActivitiesImpl implements MainWorkflowActivities {

    @Override
    public void notificationStart(List<String> entryData) {
        log.info("1 *** start process with entryData={}", entryData);
    }
}
