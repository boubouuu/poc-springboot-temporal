// src/main/java/com/poc/workflow/TestWorkflowImpl.java
package fr.boubou.poc.springboottemporal.workflow;

import fr.boubou.poc.springboottemporal.activities.ItemActivities;
import fr.boubou.poc.springboottemporal.activities.MainWorkflowActivities;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.failure.ActivityFailure;
import io.temporal.workflow.Async;
import io.temporal.workflow.ChildWorkflowOptions;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;

import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainWorkflowImpl implements MainWorkflow {

    private volatile boolean cancelled = false;
    private String currentState = "init";

    RetryOptions defaultRetry = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setBackoffCoefficient(2.0)
            .setMaximumAttempts(5)
            .build();

    private final MainWorkflowActivities activities = Workflow.newActivityStub(
            MainWorkflowActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(15))
                    .setRetryOptions(defaultRetry)
                    .build());

    private final ItemActivities itemActivities = Workflow.newActivityStub(
            ItemActivities.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(15))
                    .setRetryOptions(defaultRetry)
                    .build());

    @Override
    public Map<String, String> run(List<String> items) {
        Map<String, String> result = new HashMap<>();
        activities.notificationStart(items);

        Map<String, Promise<String>> futures = new LinkedHashMap<>();
        for (String item : items) {
            ItemWorkflow child = Workflow.newChildWorkflowStub(ItemWorkflow.class, ChildWorkflowOptions.newBuilder().build());
            futures.put(item, Async.function(child::run, item));
        }
        Promise.allOf(futures.values()).get();

        for (var e : futures.entrySet()) {
            try {
                result.put(e.getKey(), e.getValue().get());
            } catch (ActivityFailure af) {
                result.put(e.getKey(), "ERROR: " + af.getMessage());
            }
        }
        return result;
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
