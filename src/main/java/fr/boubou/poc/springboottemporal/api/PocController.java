
package fr.boubou.poc.springboottemporal.api;

import fr.boubou.poc.springboottemporal.workflow.TestWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/poc")
public class PocController {

    private final WorkflowClient client;
    private final WorkflowOptions opts;

    public PocController(WorkflowClient client) {
        this.client = client;
        this.opts = WorkflowOptions.newBuilder()
                .setTaskQueue("test-tq")
                .build();
    }

    @GetMapping("/test")
    public StartResponse start(@RequestParam(defaultValue = "corr-1") String correlationId,
                               @RequestParam(required = false) String workflowId) {
        WorkflowOptions o = workflowId == null ? opts :
                WorkflowOptions.newBuilder(opts).setWorkflowId(workflowId).build();
        TestWorkflow wf = client.newWorkflowStub(TestWorkflow.class, o);
        var exec = WorkflowClient.start(wf::run, correlationId);
        return new StartResponse(exec.getWorkflowId(), exec.getRunId());
    }

    @GetMapping("/test/{id}/state")
    public String state(@PathVariable String id) {
        TestWorkflow wf = client.newWorkflowStub(TestWorkflow.class, id);
        return wf.state();
    }

    @GetMapping("/test/{id}/cancel")
    public String cancel(@PathVariable String id) {
        TestWorkflow wf = client.newWorkflowStub(TestWorkflow.class, id);
        wf.cancel();
        return "cancel signalled";
    }

    public record StartResponse(String workflowId, String runId) {
    }
}
