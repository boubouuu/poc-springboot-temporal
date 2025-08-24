package fr.boubou.poc.springboottemporal.activities;

import fr.boubou.poc.springboottemporal.workflow.StepStatus;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface StepAActivities {

  @ActivityMethod String startStepA(String entryData);
}
