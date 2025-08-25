package fr.boubou.poc.springboottemporal.workflow;

import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

import java.util.List;
import java.util.Map;

@WorkflowInterface
public interface MainWorkflow {

    @WorkflowMethod
    Map<String, String> run(List<String> entryData);

    @SignalMethod
     void cancel();

    @QueryMethod
    String state();
}
