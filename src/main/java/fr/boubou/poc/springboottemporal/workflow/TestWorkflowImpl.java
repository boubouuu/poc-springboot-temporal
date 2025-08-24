// src/main/java/com/poc/workflow/TestWorkflowImpl.java
package fr.boubou.poc.springboottemporal.workflow;

import fr.boubou.poc.springboottemporal.activities.StepAActivities;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import java.time.Duration;

public class TestWorkflowImpl implements TestWorkflow {
  private volatile boolean cancelled = false;
  private String currentState = "init";

  private final StepAActivities activities = Workflow.newActivityStub(
      StepAActivities.class,
      ActivityOptions.newBuilder()
          .setStartToCloseTimeout(Duration.ofSeconds(15))
          .setRetryOptions(RetryOptions.newBuilder()
              .setInitialInterval(Duration.ofSeconds(1))
              .setBackoffCoefficient(2.0)
              .setMaximumAttempts(5)
              .build())
          .build());

  @Override
  public String run(String entryData) {
      return activities.startStepA(entryData);
  }

  private String finalizeState(String s) { currentState = s; return s; }

  @Override public void cancel() { this.cancelled = true; currentState = "cancelling"; }
  @Override public String state() { return currentState; }
}
