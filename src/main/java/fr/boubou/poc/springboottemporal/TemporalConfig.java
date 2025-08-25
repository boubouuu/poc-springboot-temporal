package fr.boubou.poc.springboottemporal;

import fr.boubou.poc.springboottemporal.activities.ItemActivitiesImpl;
import fr.boubou.poc.springboottemporal.activities.StatusActivitiesImpl;
import fr.boubou.poc.springboottemporal.activities.MainWorkflowActivitiesImpl;
import fr.boubou.poc.springboottemporal.workflow.ItemWorkflowImpl;
import fr.boubou.poc.springboottemporal.workflow.MainWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker; import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Value; import org.springframework.context.annotation.*;

@Configuration
public class TemporalConfig {
  @Value("${temporal.target}") String target;
  @Value("${temporal.namespace:default}") String namespace;
  @Value("${temporal.task-queue:test-tq}") String taskQueue;

    @Bean
    WorkflowServiceStubs service(@Value("${temporal.target}") String target) {
        WorkflowServiceStubsOptions stubsOpts = WorkflowServiceStubsOptions.newBuilder()
                .setTarget(target)
                .build();
        return WorkflowServiceStubs.newServiceStubs(stubsOpts);
    }

    @Bean
    WorkflowClient client(WorkflowServiceStubs service,
                          @Value("${temporal.namespace:default}") String namespace) {
        WorkflowClientOptions clientOpts = WorkflowClientOptions.newBuilder()
                .setNamespace(namespace)
                .build();
        return WorkflowClient.newInstance(service, clientOpts);
    }

  @Bean WorkerFactory workerFactory(WorkflowClient client) {
    WorkerFactory f = WorkerFactory.newInstance(client);
    Worker w = f.newWorker(taskQueue);
    w.registerWorkflowImplementationTypes(MainWorkflowImpl.class, ItemWorkflowImpl.class);
    w.registerActivitiesImplementations(new MainWorkflowActivitiesImpl(), new StatusActivitiesImpl(), new ItemActivitiesImpl());
    f.start();
    return f;
  }
}
