package fr.boubou.poc.springboottemporal.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

import java.util.List;

@ActivityInterface
public interface MainWorkflowActivities {

    @ActivityMethod
    void notificationStart(List<String> entryData);
}
