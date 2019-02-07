package com.packtpub.mmj.conductor.client.worker;

import com.netflix.conductor.client.http.MetadataClient;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.task.WorkflowTaskCoordinator;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.packtpub.mmj.conductor.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskSampleWorker {

  private static final Logger LOG = LoggerFactory.getLogger(TaskSampleWorker.class);

  // Number of threads used to execute workers.
  // To avoid starvation, should be same or more than number of workers
  private static final int THREAD_COUNT = 3;

  @Autowired
  private TaskClient taskClient;

  @Autowired
  private MetadataClient metadataClient;

  /**
   * Poll the conductor for task executions.
   */
  public void initConductorPolling() {

    final Worker worker = new ConductorWorker(
        Constants.TASK_SAMPLE);

    // Create WorkflowTaskCoordinator
    final WorkflowTaskCoordinator.Builder builder = new WorkflowTaskCoordinator.Builder();
    final WorkflowTaskCoordinator coordinator = builder.withWorkers(worker)
        .withThreadCount(THREAD_COUNT)
        .withTaskClient(taskClient).build();

    // Start for polling and execution of the tasks
    coordinator.init();
    LOG.info("{} polling initiated.", Constants.TASK_SAMPLE);
  }

  private TaskDef getTaskDef(String taskName) {
    try {
      return metadataClient.getTaskDef(taskName);
    } catch (Exception ex) {
      //LOG.info("Task Definition not found for " + taskName + " , Exception : ", ex);
    }
    return null;
  }
}
