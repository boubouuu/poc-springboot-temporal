// src/main/java/com/poc/workflow/TestWorkflow.java
package fr.boubou.poc.springboottemporal.workflow;

import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface TestWorkflow {

    @WorkflowMethod
    String run(String entryData);

    @SignalMethod
     void cancel();

    @QueryMethod
    String state();
}
