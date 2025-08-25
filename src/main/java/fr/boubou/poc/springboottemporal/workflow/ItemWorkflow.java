package fr.boubou.poc.springboottemporal.workflow;

import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ItemWorkflow {

    @WorkflowMethod
    String run(String item);

    @SignalMethod
    void cancel();

    @QueryMethod
    String state();
}
