// src/main/java/com/poc/workflow/TestWorkflowImpl.java
package fr.boubou.poc.springboottemporal.workflow;

import fr.boubou.poc.springboottemporal.activities.ItemActivities;
import fr.boubou.poc.springboottemporal.activities.StatusActivities;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class ItemWorkflowImpl implements ItemWorkflow {

    private volatile boolean cancelled = false;
    private String currentState = "init";

    RetryOptions defaultRetry = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setBackoffCoefficient(2.0)
            .setMaximumAttempts(5)
            .build();

    private final ItemActivities itemActivities = Workflow.newActivityStub(
            ItemActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(15))
                    .setRetryOptions(defaultRetry)
                    .build());

    RetryOptions pollRetry = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(5))
            .setBackoffCoefficient(1.0)
            .setMaximumAttempts(120)
            .build();

    private final StatusActivities statusActivities = Workflow.newActivityStub(
            StatusActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(15))
                    .setRetryOptions(pollRetry)
                    .build());

    @Override
    public String run(String item) {
        String taskId = itemActivities.process(item);
        return statusActivities.pollStringProcessStatus(taskId);
    }

    private String finalizeState(String s) {
        currentState = s;
        return s;
    }

    @Override
    public void cancel() {
        this.cancelled = true;
        currentState = "cancelling";
    }

    @Override
    public String state() {
        return currentState;
    }
}
