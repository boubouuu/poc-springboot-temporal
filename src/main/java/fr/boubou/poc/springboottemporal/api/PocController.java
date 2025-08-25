
package fr.boubou.poc.springboottemporal.api;

import fr.boubou.poc.springboottemporal.workflow.MainWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public StartResponse start() {
        MainWorkflow wf = client.newWorkflowStub(MainWorkflow.class, opts);
        var exec = WorkflowClient.start(wf::run, List.of("zui", "Ljk", "lul", "mdr", "ptdr", "xd", "xptdr", "yolo", "swag", "tkt", "tkt", "tkta", "tktb"));
        return new StartResponse(exec.getWorkflowId(), exec.getRunId());
    }

    public record StartResponse(String workflowId, String runId) {
    }
}
