package fr.boubou.poc.springboottemporal.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface StatusActivities {

    @ActivityMethod
    String pollStringProcessStatus(String taskId);
}
