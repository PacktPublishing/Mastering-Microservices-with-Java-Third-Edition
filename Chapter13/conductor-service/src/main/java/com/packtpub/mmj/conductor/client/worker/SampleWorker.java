package com.packtpub.mmj.conductor.client.worker;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleWorker implements Worker {

  private static final Logger LOG = LoggerFactory.getLogger(SampleWorker.class);
  private final String taskDefName;
  private final TaskDef taskDef;

  public SampleWorker(final String taskDefName, TaskDef taskDef) {
    this.taskDefName = taskDefName;
    this.taskDef = taskDef;
  }

  @Override
  public String getTaskDefName() {
    return taskDefName;
  }

  @Override
  public TaskResult execute(final Task task) {

    LOG.info(" {} Execution Started", taskDefName);
    final TaskResult result = new TaskResult(task);

    if (null != taskDef && !taskDef.getOwnerApp().equalsIgnoreCase("OTRSApp")) {

      result.setReasonForIncompletion("SampleWorker is not bound to work on" + taskDefName);
      result.setStatus(Status.FAILED);
      LOG.info("SampleWorker returning as it is not bound to work on" + taskDefName);
      return result;
    }

    LOG.info(" {} Execution Completed", taskDefName);

    return result;
  }

}
