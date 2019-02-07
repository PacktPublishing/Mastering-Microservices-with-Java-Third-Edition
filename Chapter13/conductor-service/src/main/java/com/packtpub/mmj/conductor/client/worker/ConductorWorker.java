package com.packtpub.mmj.conductor.client.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.tasks.TaskResult.Status;
import com.packtpub.mmj.conductor.common.Constants;
import com.packtpub.mmj.conductor.domain.valueobject.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConductorWorker implements Worker {

  private static final Logger LOG = LoggerFactory.getLogger(ConductorWorker.class);
  private final String taskDefName;

  public ConductorWorker(final String taskDefName) {
    this.taskDefName = taskDefName;
  }

  @Override
  public String getTaskDefName() {
    return taskDefName;
  }

  @Override
  public TaskResult execute(final Task task) {
    LOG.info("Executing {}", taskDefName);
    final TaskResult result = new TaskResult(task);

    // Recommended to use ObjectMapper as bean if planning to use it.
    final ObjectMapper mapper = new ObjectMapper();
    // TODO: Validate getInputData
    final Event input = mapper.convertValue(task.getInputData().get(Constants.EVENT), Event.class);

    if ("UserID1".trim().equals(input.getUserID())) {
      result.getOutputData().put(Constants.APPROVED, Boolean.TRUE);
    } else {
      result.getOutputData().put(Constants.APPROVED, Boolean.FALSE);
    }

    // Register the output of the task
    result.getOutputData().put("actionTakenBy", "Admin");
    result.getOutputData().put("amount", 1000);
    result.getOutputData().put("period", 4);
    result.setStatus(Status.COMPLETED);

    return result;
  }

}
